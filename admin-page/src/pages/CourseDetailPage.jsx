import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { api } from '../api/client.js';
 
const TYPES = ['NOTES', 'MIDTERM', 'FINAL'];
 
function CourseDetailPage() {
  const { id } = useParams();
  const [course, setCourse] = useState(null);
  const [resources, setResources] = useState([]);
  const [links, setLinks] = useState([]);
 
  const [resourceForm, setResourceForm] = useState({ title: '', type: 'NOTES', file: null });
  const [linkForm, setLinkForm] = useState({ topic: '', title: '', url: '' });
 
  function loadAll() {
    api.getCourse(id).then(setCourse);
    api.getResources(id).then(setResources);
    api.getLinks(id).then(setLinks);
  }
 
  useEffect(() => { loadAll(); }, [id]);
 
  async function handleUpload(e) {
    e.preventDefault();
    if (!resourceForm.file) return alert('Pick a file first');
 
    const formData = new FormData();
    formData.append('file', resourceForm.file);
    formData.append('title', resourceForm.title);
    formData.append('courseId', id);
    formData.append('type', resourceForm.type);
 
    await api.uploadResource(formData);
    setResourceForm({ title: '', type: 'NOTES', file: null });
    loadAll();
  }
 
  async function handleDeleteResource(resId) {
    if (!confirm('Delete this resource?')) return;
    await api.deleteResource(resId);
    loadAll();
  }
 
  async function handleAddLink(e) {
    e.preventDefault();
    await api.addLink(id, linkForm);
    setLinkForm({ topic: '', title: '', url: '' });
    loadAll();
  }
 
  async function handleDeleteLink(linkId) {
    if (!confirm('Delete this link?')) return;
    await api.deleteLink(linkId);
    loadAll();
  }
 
  if (!course) return <p>Loading...</p>;
 
  return (
    <div>
      <Link to="/" className="back-link">&larr; Back to courses</Link>
      <h1>{course.courseName} <span className="code">{course.code}</span></h1>
      <p className="ledger-meta">Year: {course.yearLevel}</p>
 
      <h2>Upload Resource</h2>
      <div className="panel">
        <form onSubmit={handleUpload}>
          <div className="field-row">
            <input
              placeholder="Title"
              value={resourceForm.title}
              onChange={e => setResourceForm({ ...resourceForm, title: e.target.value })}
              required
            />
            <select
              value={resourceForm.type}
              onChange={e => setResourceForm({ ...resourceForm, type: e.target.value })}
            >
              {TYPES.map(t => <option key={t} value={t}>{t}</option>)}
            </select>
            <input
              type="file"
              accept=".pdf,image/jpeg,image/png"
              onChange={e => setResourceForm({ ...resourceForm, file: e.target.files[0] })}
            />
          </div>
          <button type="submit">Upload</button>
        </form>
      </div>
 
      <h2>Resources</h2>
      <div className="card-grid">
        {resources.length === 0 && <p className="ledger-meta">No resources uploaded yet.</p>}
        {resources.map(r => (
          <div key={r.id} className="item-card">
            <div className="item-card-title">{r.title}</div>
            <div className="item-card-footer">
              <span className={`badge badge-${r.type.toLowerCase()}`}>{r.type}</span>
              <div className="item-card-actions">
                <a
                  className="btn-link"
                  href={`http://localhost:8080${r.downloadUrl}`}
                  target="_blank"
                  rel="noreferrer"
                >
                  Download
                </a>
                <button className="danger" onClick={() => handleDeleteResource(r.id)}>Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
 
      <h2>Add External Link</h2>
      <div className="panel">
        <form onSubmit={handleAddLink}>
          <div className="field-row">
            <input
              placeholder="Topic"
              value={linkForm.topic}
              onChange={e => setLinkForm({ ...linkForm, topic: e.target.value })}
            />
            <input
              placeholder="Title"
              value={linkForm.title}
              onChange={e => setLinkForm({ ...linkForm, title: e.target.value })}
              required
            />
            <input
              placeholder="URL"
              value={linkForm.url}
              onChange={e => setLinkForm({ ...linkForm, url: e.target.value })}
              required
            />
          </div>
          <button type="submit">Add Link</button>
        </form>
      </div>
 
      <h2>External Links</h2>
      <div className="card-grid">
        {links.length === 0 && <p className="ledger-meta">No external links added yet.</p>}
        {links.map(l => (
          <div key={l.id} className="item-card">
            <div className="item-card-title">
              <a href={l.url} target="_blank" rel="noreferrer">{l.title}</a>
            </div>
            <div className="item-card-footer">
              <span className="item-card-topic">{l.topic}</span>
              <div className="item-card-actions">
                <button className="danger" onClick={() => handleDeleteLink(l.id)}>Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
 
export default CourseDetailPage;