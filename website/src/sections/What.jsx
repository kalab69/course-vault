function What() {
  return (
    <section id="what" className="what-is-coursevault">
      <div className="section-label">What is CourseVault?</div>

      <h2>
        Everything you need to study. <span>All in one place.</span>
      </h2>

      <p className="section-description">
        CourseVault brings together useful study resources, external learning
        links, and AI-powered course summaries so you can spend less time
        searching and more time learning.
      </p>

      <div className="feature-grid">
        {/* TELEGRAM */}
        <div className="feature-card">
          <h3>
            No more scattered <br /> resources from Telegram
          </h3>
          <div className="telegram-illustration">
            <div className="phone">
              <div className="phone-header">
                <img
                  src="https://cdn.simpleicons.org/telegram"
                  alt="Telegram"
                />
                <span>Study Group</span>
              </div>
              <div className="message">Can anyone share <br /> DSA notes?</div>
              <div className="message">Exam papers??</div>
              <div className="message">Check this drive</div>
              <div className="message">Last year's exam?</div>
            </div>
            <div className="floating-file file-one">📄 DSA Notes.pdf</div>
            <div className="floating-file file-two">📄 Database Exam.pdf</div>
            <div className="telegram-x">×</div>
          </div>
          <p>
            Stop wasting time searching through endless messages, old files,
            and broken links.
          </p>
          <strong>Get organized. Save time. Study better.</strong>
        </div>

        {/* EXTERNAL LINKS */}
        <div className="feature-card">
          <h3>
            External links for <br /> better learning
          </h3>
          <div className="links-illustration">
            <div className="browser-window">
              <div className="browser-bar">
                <span></span>
                <span></span>
                <span></span>
              </div>
              <div className="fake-url">Search learning resources...</div>
              <div className="resource-link">
                <img
                  src="https://cdn.simpleicons.org/youtube"
                  alt="YouTube"
                />
                <div>
                  <strong>YouTube</strong>
                  <small>Data Structures Full Course</small>
                </div>
                <span>↗</span>
              </div>
              <div className="resource-link">
                <div className="resource-logo w3">W3</div>
                <div>
                  <strong>W3Schools</strong>
                  <small>C++ Programming Tutorial</small>
                </div>
                <span>↗</span>
              </div>
              <div className="resource-link">
                <img
                  src="https://cdn.simpleicons.org/geeksforgeeks"
                  alt="GeeksforGeeks"
                />
                <div>
                  <strong>GeeksforGeeks</strong>
                  <small>Algorithms & Data Structures</small>
                </div>
                <span>↗</span>
              </div>
            </div>
          </div>
          <p>
            Find useful external resources from YouTube, W3Schools,
            GeeksforGeeks and more — all organized around your courses.
          </p>
          <strong>Curated. Useful. Easy to access.</strong>
        </div>

        {/* AI SUMMARY */}
        <div className="feature-card">
          <h3>
            AI summary for <br /> every course
          </h3>
          <div className="ai-illustration">
            <div className="ai-card">
              <div className="ai-title">
                <div className="ai-icon">✦</div>
                <strong>AI Summary</strong>
              </div>
              <p>
                This course covers the fundamental concepts of Data Structures
                including arrays, linked lists, stacks, queues, trees, and
                graphs.
              </p>
              <div className="summary-lines">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
          <p>
            Get an AI-generated overview of a course to quickly understand what
            it covers before diving into the resources.
          </p>
          <strong>Understand faster. Study smarter.</strong>
        </div>

        {/* NO SIGN UP */}
        <div className="feature-card">
          <h3>
            No sign up. <br /> Just access.
          </h3>
          <div className="no-signup-illustration">
            <div className="user-circle">
              <div className="user-head"></div>
              <div className="user-body"></div>
              <div className="slash"></div>
            </div>
            <div className="no-signup-badge">✓ No Sign Up Needed</div>
          </div>
          <p>
            No account. No hassle. Just open CourseVault, explore the courses,
            and get the resources you need.
          </p>
          <strong>Simple. Fast. For students.</strong>
        </div>
      </div>

      <div className="cs-notice">
        <div className="cs-icon">&lt;/&gt;</div>
        <div>
          <strong>Currently focused on Computer Science students.</strong>
          <p>More courses and study areas coming soon.</p>
        </div>
      </div>
    </section>
  );
}

export default What;