import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const API_BASE_URL = "http://localhost:8080";

// Default/mock initial list representing downloaded resources from your reference image
const INITIAL_DOWNLOADED_FILES = [
  {
    id: 1,
    fileName: "1_Communicative_English_Language_Skills_I_MODULE.pdf",
    fileSize: "1.7 MB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/1/download",
  },
  {
    id: 2,
    fileName: "239_descrete_math.pdf",
    fileSize: "104 KB",
    downloadedAt: "Oct 03, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/2/download",
  },
  {
    id: 3,
    fileName: "34_lecture_6.pdf",
    fileSize: "562 KB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/3/download",
  },
  {
    id: 4,
    fileName: "58_eng_mid.pdf",
    fileSize: "255 KB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/4/download",
  },
  {
    id: 5,
    fileName: "59_eng_mid.pdf",
    fileSize: "200 KB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/5/download",
  },
  {
    id: 6,
    fileName: "60_eng_mid.pdf",
    fileSize: "261 KB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/6/download",
  },
  {
    id: 7,
    fileName: "61_eng_mid.pdf",
    fileSize: "237 KB",
    downloadedAt: "Sep 30, 2026",
    fileType: "pdf",
    downloadUrl: "/api/resources/7/download",
  },
];

function MyCourses() {
  const [downloadedFiles, setDownloadedFiles] = useState([]);
  const [loading, setLoading] = useState(true);

  // Load downloaded files (from LocalStorage or API, falling back to initial data)
  useEffect(() => {
    const fetchDownloadedResources = async () => {
      try {
        setLoading(true);

        // Retrieve saved files from localStorage if available
        const savedLocal = localStorage.getItem("coursevault_downloaded_resources");
        if (savedLocal) {
          setDownloadedFiles(JSON.parse(savedLocal));
        } else {
          // Fallback to local default state or API call
          setDownloadedFiles(INITIAL_DOWNLOADED_FILES);
          localStorage.setItem(
            "coursevault_downloaded_resources",
            JSON.stringify(INITIAL_DOWNLOADED_FILES)
          );
        }
      } catch (err) {
        console.error("Failed to load downloaded files:", err);
        setDownloadedFiles(INITIAL_DOWNLOADED_FILES);
      } finally {
        setLoading(false);
      }
    };

    fetchDownloadedResources();
  }, []);

  // Sync state changes with localStorage
  const updateFilesList = (newList) => {
    setDownloadedFiles(newList);
    localStorage.setItem("coursevault_downloaded_resources", JSON.stringify(newList));
  };

  // Open PDF / Image in a direct window view
  const handleOpenFile = (file) => {
    const fileUrl = file.downloadUrl.startsWith("http")
      ? file.downloadUrl
      : `${API_BASE_URL}${file.downloadUrl}`;

    // Open file directly in a new browser tab where PDF/Images can render
    window.open(fileUrl, "_blank", "noopener,noreferrer");
  };

  // Delete file from downloaded resources list
  const handleDeleteFile = (e, id) => {
    e.stopPropagation();
    const updated = downloadedFiles.filter((item) => item.id !== id);
    updateFilesList(updated);
  };

  // Render appropriate file icon
  const getFileIcon = (fileName) => {
    const lower = (fileName || "").toLowerCase();
    if (lower.endsWith(".pdf")) {
      return (
        <svg className="mc-file-icon pdf" viewBox="0 0 24 24" fill="none" stroke="currentColor">
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M7 21h10a2 2 0 002-2V9.414a1 1 0 00-.293-.707l-5.414-5.414A1 1 0 0012.586 3H7a2 2 0 00-2 2v14a2 2 0 002 2z"
          />
        </svg>
      );
    }
    return (
      <svg className="mc-file-icon image" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth="2"
          d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"
        />
      </svg>
    );
  };

  return (
    <div className="mc-page-layout">
      {/* Sidebar Navigation */}
      <aside className="mc-sidebar">
        <div className="mc-sidebar-brand">CourseVault</div>
        <nav className="mc-sidebar-nav">
          <Link to="/dashboard" className="mc-nav-link">
            Dashboard
          </Link>
          <Link to="/browse-year" className="mc-nav-link">
            Browse Year
          </Link>
        </nav>
      </aside>

      {/* Main Content View Area */}
      <main className="mc-main-content">
        {/* Top Bar Header */}
        <header className="mc-top-header">
          <div className="mc-header-spacer"></div>
          <nav className="mc-header-nav">
            <Link to="/" className="mc-header-link">
              Home
            </Link>
            <span className="mc-nav-divider">|</span>
            <Link to="/my-courses" className="mc-header-link active">
              My Course
            </Link>
            <span className="mc-nav-divider">|</span>
            <Link to="/courses" className="mc-header-link">
              Browse
            </Link>
          </nav>
        </header>

        {/* Header Title Section */}
        <section className="mc-content-header">
          <h1 className="mc-page-title">My Resources</h1>
          <p className="mc-page-subtitle">Files you have downloaded</p>
          <div className="mc-badge-count">
            {downloadedFiles.length} {downloadedFiles.length === 1 ? "file" : "files"} downloaded
          </div>
        </section>

        {/* Files List Container */}
        <section className="mc-files-container">
          {loading ? (
            <div className="mc-loading-box">Loading downloaded resources...</div>
          ) : downloadedFiles.length === 0 ? (
            <div className="mc-empty-box">
              <p>No downloaded resources available yet.</p>
              <Link to="/courses" className="mc-browse-btn">
                Browse Courses
              </Link>
            </div>
          ) : (
            <div className="mc-files-list">
              {downloadedFiles.map((file) => (
                <div key={file.id} className="mc-file-card">
                  <div className="mc-file-info-group">
                    <div className="mc-icon-wrapper">{getFileIcon(file.fileName)}</div>
                    <div className="mc-file-details">
                      <div className="mc-file-title" title={file.fileName}>
                        {file.fileName}
                      </div>
                      <div className="mc-file-meta">
                        <span>{file.fileSize}</span>
                        <span className="mc-meta-bullet">•</span>
                        <span>{file.downloadedAt}</span>
                      </div>
                    </div>
                  </div>

                  <div className="mc-file-actions">
                    <button
                      className="mc-open-btn"
                      onClick={() => handleOpenFile(file)}
                    >
                      Open
                    </button>
                    <button
                      className="mc-delete-btn"
                      onClick={(e) => handleDeleteFile(e, file.id)}
                      title="Delete resource"
                    >
                      <svg
                        className="mc-trash-icon"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                      >
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth="2"
                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                        />
                      </svg>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>
      </main>
    </div>
  );
}

export default MyCourses;