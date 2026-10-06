import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

const API_BASE_URL = "https://course-vault-production-5ad8.up.railway.app";

function CourseDetail() {
  const { id } = useParams();

  // Data states
  const [course, setCourse] = useState(null);
  const [resources, setResources] = useState([]);
  const [links, setLinks] = useState([]);

  // AI Summary & Caching
  const [aiSummary, setAiSummary] = useState(null);
  const [aiLoading, setAiLoading] = useState(false);
  const [aiError, setAiError] = useState(null);

  // General state
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState("ALL"); // ALL, NOTES, MIDTERM, FINAL, LINKS, AI_SUMMARY

  // 1. Fetch initial course metadata, resources, and links
  useEffect(() => {
    const fetchCourseData = async () => {
      try {
        setLoading(true);
        setError(null);

        const [courseRes, resourcesRes, linksRes] = await Promise.all([
          fetch(`${API_BASE_URL}/api/courses/${id}`),
          fetch(`${API_BASE_URL}/api/courses/${id}/course-resources`),
          fetch(`${API_BASE_URL}/api/courses/${id}/links`),
        ]);

        if (!courseRes.ok) throw new Error("Course not found");

        const courseData = await courseRes.json();
        const resourcesData = resourcesRes.ok ? await resourcesRes.json() : [];
        const linksData = linksRes.ok ? await linksRes.json() : [];

        setCourse(courseData);
        setResources(resourcesData);
        setLinks(linksData);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchCourseData();
  }, [id]);

  // Direct File Download Handler
  const handleDownload = (e, downloadUrl, fileName) => {
    if (e) e.stopPropagation();
    const targetUrl = downloadUrl.startsWith("http")
      ? downloadUrl
      : `${API_BASE_URL}${downloadUrl}`;
    const link = document.createElement("a");
    link.href = targetUrl;
    link.download = fileName || "download";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  // AI Summary Fetch & Cache
  const fetchAiSummary = async () => {
    if (aiSummary || aiLoading) return;

    try {
      setAiLoading(true);
      setAiError(null);

      const response = await fetch(`${API_BASE_URL}/api/courses/${id}/ai-description`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
      });

      if (!response.ok) {
        throw new Error(`Server returned status: ${response.status}`);
      }

      const data = await response.json();
      const rawText = data.description || (typeof data === "string" ? data : "");
      setAiSummary(rawText);
    } catch (err) {
      setAiError(err.message || "Failed to generate AI summary.");
    } finally {
      setAiLoading(false);
    }
  };

  const handleTabChange = (tabKey) => {
    setActiveTab(tabKey);
    if (tabKey === "AI_SUMMARY") {
      fetchAiSummary();
    }
  };

  // Parse AI Markdown to formatted cards
  const parseAiMarkdown = (rawText) => {
    if (!rawText) return [];

    const cleanedText = rawText
      .replace(/â/g, "—")
      .replace(/â¢/g, "•")
      .replace(/Bâ/g, "B-");

    const lines = cleanedText.split("\n");
    const sections = [];
    let currentSection = null;

    lines.forEach((line) => {
      const trimmed = line.trim();
      if (!trimmed) return;

      if (trimmed.startsWith("**") && trimmed.includes("**")) {
        const title = trimmed.replace(/\*\*/g, "").trim();
        currentSection = { title, items: [] };
        sections.push(currentSection);
      } else if (trimmed.startsWith("-")) {
        const bulletText = trimmed.substring(1).trim();
        if (!currentSection) {
          currentSection = { title: "Overview", items: [] };
          sections.push(currentSection);
        }
        currentSection.items.push({ type: "bullet", text: bulletText });
      } else {
        if (!currentSection) {
          currentSection = { title: "Overview", items: [] };
          sections.push(currentSection);
        }
        currentSection.items.push({ type: "text", text: trimmed });
      }
    });

    return sections;
  };

  const getFilteredResources = () => {
    if (activeTab === "NOTES") return resources.filter((r) => r.type === "NOTES");
    if (activeTab === "MIDTERM") return resources.filter((r) => r.type === "MIDTERM");
    if (activeTab === "FINAL") return resources.filter((r) => r.type === "FINAL");
    return resources;
  };

  if (loading) {
    return (
      <div className="cd-page-wrapper">
        <div className="cd-container">
          <div className="cd-loading-state">
            <span className="cd-spinner">⟳</span> Loading course workspace...
          </div>
        </div>
      </div>
    );
  }

  if (error || !course) {
    return (
      <div className="cd-page-wrapper">
        <div className="cd-container">
          <Link to="/courses" className="cd-back-btn">
            ← Back to Courses
          </Link>
          <div className="cd-error-box">{error || "Unable to load course parameters."}</div>
        </div>
      </div>
    );
  }

  const filteredResources = getFilteredResources();
  const parsedAiSections = parseAiMarkdown(aiSummary);

  return (
    <div className="cd-page-wrapper">
      <div className="cd-container">
        
        {/* Course Header */}
        <div className="cd-header-row">
          <Link to="/courses" className="cd-back-btn">
            ← Back to Courses
          </Link>
          <div className="cd-title-group">
            <span className="cd-code-pill">{course.code}</span>
            <h1 className="cd-course-title">{course.courseName}</h1>
          </div>
        </div>

        {/* Navigation Tabs Bar */}
        <div className="cd-tab-bar">
          <button
            className={`cd-tab-item ${activeTab === "ALL" ? "active" : ""}`}
            onClick={() => handleTabChange("ALL")}
          >
            All
          </button>
          <button
            className={`cd-tab-item ${activeTab === "NOTES" ? "active" : ""}`}
            onClick={() => handleTabChange("NOTES")}
          >
            📄 Notes
          </button>
          <button
            className={`cd-tab-item ${activeTab === "MIDTERM" ? "active" : ""}`}
            onClick={() => handleTabChange("MIDTERM")}
          >
            📝 Midterm Exams
          </button>
          <button
            className={`cd-tab-item ${activeTab === "FINAL" ? "active" : ""}`}
            onClick={() => handleTabChange("FINAL")}
          >
            🎓 Final Exams
          </button>
          <button
            className={`cd-tab-item ${activeTab === "LINKS" ? "active" : ""}`}
            onClick={() => handleTabChange("LINKS")}
          >
            🔗 External Links
          </button>
          <button
            className={`cd-tab-item ai-tab ${activeTab === "AI_SUMMARY" ? "active" : ""}`}
            onClick={() => handleTabChange("AI_SUMMARY")}
          >
            ✨ AI Summary
          </button>
        </div>

        {/* Content Tabs */}
        {activeTab !== "LINKS" && activeTab !== "AI_SUMMARY" && (
          <div className="cd-content-block">
            {filteredResources.length === 0 ? (
              <div className="cd-empty-card">
                No course files uploaded for this filter yet.
              </div>
            ) : (
              <div className="cd-resource-list">
                {filteredResources.map((res) => {
                  const downloadPath =
                    res.downloadUrl || `/api/courses/resources/${res.id}/download`;
                  return (
                    <div
                      key={res.id}
                      className="cd-resource-row"
                      onClick={(e) => handleDownload(e, downloadPath, res.title)}
                    >
                      <div className="cd-resource-left">
                        <span className="cd-check-icon">✓</span>
                        <span className="cd-resource-title">{res.title}</span>
                        <span className={`cd-type-badge ${res.type}`}>{res.type}</span>
                      </div>
                      <div className="cd-resource-actions">
                        <button
                          className="cd-action-btn download-btn"
                          onClick={(e) => handleDownload(e, downloadPath, res.title)}
                        >
                          Download ⬇
                        </button>
                      </div>
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        )}

        {/* Links Tab */}
        {activeTab === "LINKS" && (
          <div className="cd-content-block">
            {links.length === 0 ? (
              <div className="cd-empty-card">No external tutorial links available.</div>
            ) : (
              <div className="cd-links-grid">
                {links.map((link) => (
                  <a
                    key={link.id}
                    href={link.url}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="cd-link-card"
                  >
                    <span className="cd-link-topic">{link.topic || "External Resource"}</span>
                    <h3 className="cd-link-title">{link.title} ↗</h3>
                  </a>
                ))}
              </div>
            )}
          </div>
        )}

        {/* AI Summary Tab */}
        {activeTab === "AI_SUMMARY" && (
          <div className="cd-content-block">
            <div className="cd-ai-header-badge">
              <span className="cd-ai-sparkle">✨</span>
              <span className="cd-ai-badge-text">AI Generated Summary</span>
              <span className="cd-ai-disclaimer">• May not be 100% accurate</span>
            </div>

            {aiLoading && (
              <div className="cd-ai-loading-row">
                <span className="cd-spinner-animated">⟳</span>
                <span>Generating AI summary...</span>
              </div>
            )}

            {aiError && (
              <div className="cd-error-box">
                Failed to generate AI summary: {aiError}
              </div>
            )}

            {!aiLoading && !aiError && parsedAiSections.length > 0 && (
              <div className="cd-ai-sections-grid">
                {parsedAiSections.map((sec, idx) => (
                  <div key={idx} className="cd-ai-section-card">
                    <h3 className="cd-ai-section-title">{sec.title}</h3>
                    <div className="cd-ai-section-body">
                      {sec.items.map((item, itemIdx) => (
                        <div key={itemIdx} className="cd-ai-item-row">
                          {item.type === "bullet" ? (
                            <>
                              <span className="cd-ai-bullet-dot">•</span>
                              <span className="cd-ai-item-text">{item.text}</span>
                            </>
                          ) : (
                            <p className="cd-ai-paragraph">{item.text}</p>
                          )}
                        </div>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

      </div>
    </div>
  );
}

export default CourseDetail;