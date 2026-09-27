const BASE_URL = "http://localhost:8080";

export const api = {
  getCourses: (year) =>
    fetch(`${BASE_URL}/api/courses${year ? `?year=${year}` : ''}`).then(r => r.json()),
  createCourse: (data) =>
    fetch(`${BASE_URL}/api/courses`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(r => r.json()),
  updateCourse: (id, data) =>
    fetch(`${BASE_URL}/api/courses/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(r => r.json()),
  deleteCourse: (id) =>
    fetch(`${BASE_URL}/api/courses/${id}`, { method: 'DELETE' }),

  getResources: (courseId, type) =>
    fetch(`${BASE_URL}/api/courses/${courseId}/course-resources${type ? `?type=${type}` : ''}`).then(r => r.json()),
  uploadResource: (formData) =>
    fetch(`${BASE_URL}/api/course-resources`, { method: 'POST', body: formData }).then(r => r.json()),
  deleteResource: (id) =>
    fetch(`${BASE_URL}/api/course-resources/${id}`, { method: 'DELETE' }),

  getLinks: (courseId) =>
    fetch(`${BASE_URL}/api/courses/${courseId}/links`).then(r => r.json()),
  addLink: (courseId, data) =>
    fetch(`${BASE_URL}/api/courses/${courseId}/links`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(r => r.json()),
  deleteLink: (id) =>
    fetch(`${BASE_URL}/api/links/${id}`, { method: 'DELETE' }),
  getCourse: (id) => fetch(`${BASE_URL}/api/courses/${id}`).then(r => r.json()),
};