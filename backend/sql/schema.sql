create database COURSE_VAULT;
use course_vault;

-- DATAS FOR ALL THE COMPUTER SCINCE COURSES

INSERT INTO Courses (code, course_name, year_level) VALUES
('Flen1011', 'Communicative English Language Skills I', 'FIRST'),
('Math 1011', 'Mathematics for Natural Science', 'FIRST'),
('Anth1012', 'Social Anthropology', 'FIRST'),
('MCiE1012', 'Moral and Civic Education', 'FIRST'),
('CoSc 1011', 'Introduction to Computing Science', 'FIRST'),
('HiEH1012', 'History of Ethiopia and the Horn', 'FIRST'),
('PsyL1011', 'General Psychology and Life Skills', 'FIRST'),
('CoSc1012', 'Programming Fundamentals I', 'FIRST'),
('LoCT1011', 'Logic and Critical Thinking', 'FIRST'),
('EmTe1012', 'Introduction to Emerging Technologies', 'FIRST'),
('GITr1012', 'Global Trends', 'FIRST'),
('GeEH10111', 'Geography of Ethiopia and the Horn', 'FIRST'),
('Econ1012', 'Economics', 'FIRST'),
('Flen1012', 'Communicative English Language Skills II', 'FIRST'),
('MgMt1013', 'Entrepreneurship and Business Development', 'FIRST'),
('SNIE1012', 'Inclusiveness', 'FIRST'),
('SpSc1011', 'Physical Fitness', 'FIRST'),

('CoSc2011', 'Programming Fundamentals II', 'SECOND'),
('CoSc 2041', 'Fundamentals of Database Systems', 'SECOND'),
('Math2051', 'Discrete Mathematics and Combinatorics', 'SECOND'),
('CoSc2042', 'Digital Electronics and Logic Design', 'SECOND'),
('CoSc2031', 'Data Communication and Computer Networks', 'SECOND'),
('Math2041', 'Calculus I', 'SECOND'),
('CoSc2092', 'Data Structure and Algorithm Analysis', 'SECOND'),
('CoSc2054', 'Object Oriented Programming', 'SECOND'),
('CoSc 2034', 'Network and System Administration', 'SECOND'),
('Math 2012', 'Introduction to Linear Algebra', 'SECOND'),
('CoSc2022', 'Computer Architecture and Organization', 'SECOND'),
('CoSc3021', 'Machine Organization and Assembly Language', 'SECOND'),
('STAT2012', 'Introduction to Statistics and Probability Theory', 'SECOND'),

('CoSc3061', 'Software Engineering', 'THIRD'),
('CoSc3041', 'Advanced Database Systems', 'THIRD'),
('CoSc3081', 'Internet Programming I', 'THIRD'),
('CoSc3122', 'Research Methods in Computer Science', 'THIRD'),
('Math3081', 'Numerical Analysis', 'THIRD'),
('CoSc3091', 'Introduction to Algorithms', 'THIRD'),
('CoSc3022', 'Operating Systems Principles and Design', 'THIRD'),
('CoSc3052', 'Rapid Application Development', 'THIRD'),
('CoSc3112', 'Introduction to Artificial Intelligence', 'THIRD'),
('CoSc4131', 'Selected Topics in Computer Science', 'THIRD'),
('CoSc3102', 'Automata and Complexity Theory', 'THIRD'),
('CoSc3082', 'Internet Programming II', 'THIRD'),
('CoSc3121', 'Internship', 'THIRD'),

('CoSc4132', 'Comprehensive Degree Exit Exam I', 'FOURTH'),
('CoSc4121', 'Senior Project I', 'FOURTH'),
('CoSc4081', 'Mobile Application Development', 'FOURTH'),
('CoSc4031', 'Computer and Network Security', 'FOURTH'),
('CoSc4101', 'Compiler Design', 'FOURTH'),
('CoSc4072', 'Computer Graphics', 'FOURTH'),
('CoSc4122', 'Senior Project II', 'FOURTH'),
('CoSc4032', 'Wireless Communications and Mobile Computing', 'FOURTH'),
('CoSc4133', 'Comprehensive Degree Exit Exam II', 'FOURTH');


-- THE QUERY FOR EXTERNAL_LINKS FOR CORROSPONDING TABLES FOR FIRST YEAR COURSES

INSERT INTO external_link (course_id, topic, title, url) VALUES

-- =========================================================
-- 1. Communicative English Language Skills I
-- Flen1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Flen1011'),
    'Grammar',
    'Purdue OWL Grammar Guide',
    'https://owl.purdue.edu/owl/general_writing/grammar/index.html'
),
(
    (SELECT id FROM Courses WHERE code = 'Flen1011'),
    'English Writing',
    'British Council LearnEnglish',
    'https://learnenglish.britishcouncil.org/'
),

-- =========================================================
-- 2. Mathematics for Natural Science
-- Math 1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Math 1011'),
    'Algebra',
    'Khan Academy Algebra',
    'https://www.khanacademy.org/math/algebra-home'
),
(
    (SELECT id FROM Courses WHERE code = 'Math 1011'),
    'Mathematics',
    'Khan Academy Mathematics',
    'https://www.khanacademy.org/math'
),

-- =========================================================
-- 3. Social Anthropology
-- Anth1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Anth1012'),
    'Introduction to Anthropology',
    'OpenStax Introduction to Anthropology',
    'https://openstax.org/books/introduction-anthropology/pages/1-introduction'
),
(
    (SELECT id FROM Courses WHERE code = 'Anth1012'),
    'Culture',
    'OpenStax Anthropology - Culture',
    'https://openstax.org/books/introduction-anthropology/pages/3-introduction'
),

-- =========================================================
-- 4. Moral and Civic Education
-- MCiE1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'MCiE1012'),
    'Human Rights',
    'United Nations Universal Declaration of Human Rights',
    'https://www.un.org/en/about-us/universal-declaration-of-human-rights'
),
(
    (SELECT id FROM Courses WHERE code = 'MCiE1012'),
    'Global Citizenship',
    'UNESCO Global Citizenship Education',
    'https://www.unesco.org/en/global-citizenship-peace-education/need-know'
),

-- =========================================================
-- 5. Introduction to Computing Science
-- CoSc 1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc 1011'),
    'Introduction to Computer Science',
    'Harvard CS50 - Introduction to Computer Science',
    'https://cs50.harvard.edu/x/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 1011'),
    'Programming Fundamentals',
    'W3Schools Introduction to Programming',
    'https://www.w3schools.com/programming/'
),

-- =========================================================
-- 6. History of Ethiopia and the Horn
-- HiEH1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'HiEH1012'),
    'History of Ethiopia',
    'Encyclopaedia Britannica - Ethiopia',
    'https://www.britannica.com/place/Ethiopia'
),
(
    (SELECT id FROM Courses WHERE code = 'HiEH1012'),
    'Ethiopian History',
    'Library of Congress - Ethiopia Country Study',
    'https://www.loc.gov/item/93022626/'
),

-- =========================================================
-- 7. General Psychology and Life Skills
-- PsyL1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'PsyL1011'),
    'Introduction to Psychology',
    'OpenStax Psychology 2e',
    'https://openstax.org/books/psychology-2e/pages/1-introduction'
),
(
    (SELECT id FROM Courses WHERE code = 'PsyL1011'),
    'Psychology',
    'American Psychological Association - Psychology Topics',
    'https://www.apa.org/topics'
),

-- =========================================================
-- 8. Programming Fundamentals I
-- CoSc1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc1012'),
    'Programming Fundamentals',
    'W3Schools Programming',
    'https://www.w3schools.com/programming/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc1012'),
    'C Programming',
    'W3Schools C Tutorial',
    'https://www.w3schools.com/c/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc1012'),
    'Programming Practice',
    'HackerRank - C Programming',
    'https://www.hackerrank.com/domains/c'
),

-- =========================================================
-- 9. Logic and Critical Thinking
-- LoCT1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'LoCT1011'),
    'Logic',
    'Stanford Encyclopedia of Philosophy - Classical Logic',
    'https://plato.stanford.edu/entries/logic-classical/'
),
(
    (SELECT id FROM Courses WHERE code = 'LoCT1011'),
    'Critical Thinking',
    'Foundation for Critical Thinking - Defining Critical Thinking',
    'https://www.criticalthinking.org/pages/understanding-evidence/766'
),

-- =========================================================
-- 10. Introduction to Emerging Technologies
-- EmTe1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'EmTe1012'),
    'Emerging Technologies',
    'World Economic Forum - Top 10 Emerging Technologies',
    'https://www.weforum.org/publications/top-10-emerging-technologies-of-2025/'
),
(
    (SELECT id FROM Courses WHERE code = 'EmTe1012'),
    'Technology',
    'IBM Technology',
    'https://www.ibm.com/think/topics'
),

-- =========================================================
-- 11. Global Trends
-- GITr1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'GITr1012'),
    'Global Development',
    'United Nations Sustainable Development Goals',
    'https://www.un.org/sustainabledevelopment/sustainable-development-goals/'
),
(
    (SELECT id FROM Courses WHERE code = 'GITr1012'),
    'Global Data',
    'Our World in Data',
    'https://ourworldindata.org/'
),

-- =========================================================
-- 12. Geography of Ethiopia and the Horn
-- GeEH10111
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'GeEH10111'),
    'Geography of Ethiopia',
    'Encyclopaedia Britannica - Ethiopia',
    'https://www.britannica.com/place/Ethiopia'
),
(
    (SELECT id FROM Courses WHERE code = 'GeEH10111'),
    'Maps and Geography',
    'National Geographic Education',
    'https://education.nationalgeographic.org/'
),

-- =========================================================
-- 13. Economics
-- Econ1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Econ1012'),
    'Introduction to Economics',
    'OpenStax Principles of Economics 3e',
    'https://openstax.org/details/books/principles-economics-3e/'
),
(
    (SELECT id FROM Courses WHERE code = 'Econ1012'),
    'Microeconomics',
    'Khan Academy Economics',
    'https://www.khanacademy.org/economics-finance-domain'
),

-- =========================================================
-- 14. Communicative English Language Skills II
-- Flen1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Flen1012'),
    'Academic Writing',
    'Purdue OWL Academic Writing and Research',
    'https://owl.purdue.edu/owl/research_and_citation/'
),
(
    (SELECT id FROM Courses WHERE code = 'Flen1012'),
    'Research and Citation',
    'Purdue OWL Research and Citation Resources',
    'https://owl.purdue.edu/owl/research_and_citation/resources.html'
),

-- =========================================================
-- 15. Entrepreneurship and Business Development
-- MgMt1013
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'MgMt1013'),
    'Business Planning',
    'U.S. Small Business Administration - Plan Your Business',
    'https://www.sba.gov/counseling/plan-your-business/'
),
(
    (SELECT id FROM Courses WHERE code = 'MgMt1013'),
    'Entrepreneurship',
    'MIT Entrepreneurship',
    'https://entrepreneurship.mit.edu/'
),

-- =========================================================
-- 16. Inclusiveness
-- SNIE1012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'SNIE1012'),
    'Inclusion and Accessibility',
    'W3C Web Accessibility Initiative',
    'https://www.w3.org/WAI/'
),
(
    (SELECT id FROM Courses WHERE code = 'SNIE1012'),
    'Accessibility',
    'W3C Introduction to Web Accessibility',
    'https://www.w3.org/WAI/fundamentals/accessibility-intro/'
),
(
    (SELECT id FROM Courses WHERE code = 'SNIE1012'),
    'Inclusive Design',
    'W3C Accessibility, Usability and Inclusion',
    'https://www.w3.org/WAI/fundamentals/accessibility-usability-inclusion/'
),

-- =========================================================
-- 17. Physical Fitness
-- SpSc1011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'SpSc1011'),
    'Physical Activity',
    'World Health Organization - Physical Activity',
    'https://www.who.int/news-room/fact-sheets/detail/physical-activity'
),
(
    (SELECT id FROM Courses WHERE code = 'SpSc1011'),
    'Exercise Guidelines',
    'WHO Guidelines on Physical Activity',
    'https://www.ncbi.nlm.nih.gov/books/NBK566046/'
);

-- THE QUERY FOR EXTERNAL_LINKS FOR CORROSPONDING TABLES FOR SECOND YEAR COURSES

INSERT INTO external_link (course_id, topic, title, url) VALUES

-- =========================================================
-- 18. Programming Fundamentals II
-- CoSc2011
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2011'),
    'C++ Programming',
    'W3Schools C++ Tutorial',
    'https://www.w3schools.com/cpp/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2011'),
    'Object-Oriented Programming',
    'W3Schools C++ Classes and Objects',
    'https://www.w3schools.com/cpp/cpp_classes.asp'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2011'),
    'Pointers and Memory',
    'Learn C++ - Pointers',
    'https://www.learncpp.com/cpp-tutorial/introduction-to-pointers/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2011'),
    'Programming Practice',
    'HackerRank C++',
    'https://www.hackerrank.com/domains/cpp'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2011'),
    'C++ Programming',
    'freeCodeCamp C++ Course',
    'https://www.youtube.com/watch?v=vLnPwxZdW4Y'
),


-- =========================================================
-- 19. Fundamentals of Database Systems
-- CoSc 2041
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'SQL',
    'W3Schools SQL Tutorial',
    'https://www.w3schools.com/sql/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'SQL Practice',
    'SQLBolt Interactive SQL Lessons',
    'https://sqlbolt.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'Database Concepts',
    'GeeksforGeeks DBMS Tutorial',
    'https://www.geeksforgeeks.org/dbms/dbms/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'SQL Practice',
    'LeetCode Database Problems',
    'https://leetcode.com/problemset/database/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'Database Management Systems',
    'Neso Academy - DBMS',
    'https://www.nesoacademy.org/cs/14-subject'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2041'),
    'SQL',
    'CS50 - Introduction to Databases with SQL',
    'https://cs50.harvard.edu/sql/'
),


-- =========================================================
-- 20. Discrete Mathematics and Combinatorics
-- Math2051
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Math2051'),
    'Discrete Mathematics',
    'MIT OpenCourseWare - Mathematics for Computer Science',
    'https://ocw.mit.edu/courses/6-042j-mathematics-for-computer-science-fall-2010/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math2051'),
    'Combinatorics',
    'MIT OpenCourseWare - Combinatorial Analysis',
    'https://ocw.mit.edu/courses/18-211-combinatorial-analysis-fall-2014/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math2051'),
    'Discrete Mathematics',
    'GeeksforGeeks - Discrete Mathematics',
    'https://www.geeksforgeeks.org/maths/discrete-mathematics/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math2051'),
    'Discrete Mathematics',
    'TrevTutor - Discrete Mathematics',
    'https://www.youtube.com/@TrevTutor'
),


-- =========================================================
-- 21. Digital Electronics and Logic Design
-- CoSc2042
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2042'),
    'Digital Logic',
    'All About Circuits - Digital Circuits',
    'https://www.allaboutcircuits.com/textbook/digital/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2042'),
    'Logic Gates',
    'Wikiversity - Logic Gates',
    'https://en.wikiversity.org/wiki/Logic_gates'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2042'),
    'Digital Electronics',
    'Neso Academy - Digital Electronics',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2042'),
    'Digital Logic',
    'Ben Eater - Digital Electronics',
    'https://www.youtube.com/@BenEater'
),


-- =========================================================
-- 22. Data Communication and Computer Networks
-- CoSc2031
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2031'),
    'Computer Networking',
    'Cisco Networking Resources',
    'https://www.cisco.com/c/en/us/training-events/training-certifications/training/networking.html'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2031'),
    'Networking Fundamentals',
    'Cloudflare Learning Center - Computer Networks',
    'https://www.cloudflare.com/learning/network-layer/what-is-a-computer-network/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2031'),
    'Network Protocols',
    'MDN - HTTP Overview',
    'https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/Overview'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2031'),
    'Computer Networks',
    'Neso Academy - Computer Networks',
    'https://www.nesoacademy.org/cs/06-subject'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2031'),
    'Computer Networking',
    'PowerCert Animated Videos - Networking',
    'https://www.youtube.com/@PowerCertAnimatedVideos'
),


-- =========================================================
-- 23. Calculus I
-- Math2041
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Math2041'),
    'Calculus',
    'Khan Academy - Calculus 1',
    'https://www.khanacademy.org/math/calculus-1'
),
(
    (SELECT id FROM Courses WHERE code = 'Math2041'),
    'Calculus',
    'MIT OpenCourseWare - Single Variable Calculus',
    'https://ocw.mit.edu/courses/18-01sc-single-variable-calculus-fall-2010/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math2041'),
    'Calculus',
    'Professor Leonard - Calculus',
    'https://www.youtube.com/@ProfessorLeonard'
),


-- =========================================================
-- 24. Data Structure and Algorithm Analysis
-- CoSc2092
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Data Structures and Algorithms',
    'W3Schools DSA Tutorial',
    'https://www.w3schools.com/dsa/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Data Structures and Algorithms',
    'GeeksforGeeks DSA Tutorial',
    'https://www.geeksforgeeks.org/dsa/introduction-to-dsa/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Algorithm Visualization',
    'VisuAlgo',
    'https://visualgo.net/en'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Problem Solving',
    'LeetCode',
    'https://leetcode.com/problemset/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Data Structures and Algorithms',
    'Neso Academy - Data Structures',
    'https://www.nesoacademy.org/cs/10-subject'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2092'),
    'Data Structures and Algorithms',
    'Abdul Bari - Algorithms',
    'https://www.youtube.com/@abdul_bari'
),


-- =========================================================
-- 25. Object Oriented Programming
-- CoSc2054
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2054'),
    'OOP Concepts',
    'W3Schools C++ Classes and Objects',
    'https://www.w3schools.com/cpp/cpp_classes.asp'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2054'),
    'Inheritance',
    'W3Schools C++ Inheritance',
    'https://www.w3schools.com/cpp/cpp_inheritance.asp'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2054'),
    'Java OOP',
    'Oracle Java - Object-Oriented Programming Concepts',
    'https://docs.oracle.com/javase/tutorial/java/concepts/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2054'),
    'Object-Oriented Programming',
    'Programming with Mosh - Java OOP',
    'https://www.youtube.com/@programmingwithmosh'
),


-- =========================================================
-- 26. Network and System Administration
-- CoSc 2034
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc 2034'),
    'Linux Administration',
    'Linux Documentation Project',
    'https://tldp.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2034'),
    'Linux',
    'Ubuntu Server Documentation',
    'https://documentation.ubuntu.com/server/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2034'),
    'Networking',
    'Cisco Networking Resources',
    'https://www.cisco.com/c/en/us/training-events/training-certifications/training/networking.html'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2034'),
    'Linux Practice',
    'OverTheWire Wargames',
    'https://overthewire.org/wargames/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc 2034'),
    'Linux Administration',
    'Learn Linux TV',
    'https://www.youtube.com/@LearnLinuxTV'
),


-- =========================================================
-- 27. Introduction to Linear Algebra
-- Math 2012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Math 2012'),
    'Linear Algebra',
    'MIT OpenCourseWare - Linear Algebra',
    'https://ocw.mit.edu/courses/18-06sc-linear-algebra-fall-2011/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math 2012'),
    'Matrices and Vectors',
    'Khan Academy - Linear Algebra',
    'https://www.khanacademy.org/math/linear-algebra'
),
(
    (SELECT id FROM Courses WHERE code = 'Math 2012'),
    'Linear Algebra',
    '3Blue1Brown - Essence of Linear Algebra',
    'https://www.3blue1brown.com/topics/linear-algebra'
),
(
    (SELECT id FROM Courses WHERE code = 'Math 2012'),
    'Linear Algebra',
    '3Blue1Brown - Linear Algebra',
    'https://www.youtube.com/@3blue1brown'
),


-- =========================================================
-- 28. Computer Architecture and Organization
-- CoSc2022
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc2022'),
    'Computer Architecture',
    'GeeksforGeeks - Computer Organization and Architecture',
    'https://www.geeksforgeeks.org/computer-organization-architecture-tutorials/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2022'),
    'Computer Architecture',
    'Nand2Tetris',
    'https://www.nand2tetris.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2022'),
    'Computer Architecture',
    'Neso Academy - Computer Organization',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc2022'),
    'Computer Architecture',
    'Ben Eater - Build a Computer',
    'https://www.youtube.com/@BenEater'
),


-- =========================================================
-- 29. Machine Organization and Assembly Language
-- CoSc3021
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3021'),
    'Assembly Language',
    'TutorialsPoint - Assembly Programming',
    'https://www.tutorialspoint.com/assembly_programming/index.htm'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3021'),
    'x86 Assembly',
    'Intel 64 and IA-32 Architecture Manuals',
    'https://www.intel.com/content/www/us/en/developer/articles/technical/intel-sdm.html'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3021'),
    'Assembly Practice',
    'Compiler Explorer',
    'https://godbolt.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3021'),
    'Assembly Language',
    'Neso Academy - Computer Organization and Assembly',
    'https://www.youtube.com/@nesoacademy'
),


-- =========================================================
-- 30. Introduction to Statistics and Probability Theory
-- STAT2012
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Statistics',
    'OpenStax - Introductory Statistics 2e',
    'https://openstax.org/books/introductory-statistics-2e/pages/1-introduction'
),
(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Probability',
    'OpenStax - Probability',
    'https://openstax.org/books/introductory-statistics-2e/pages/3-6-probability-topics'
),
(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Probability Distributions',
    'OpenStax - Introductory Statistics',
    'https://openstax.org/books/introductory-statistics-2e/'
),
(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Statistics and Probability',
    'Khan Academy - Statistics and Probability',
    'https://www.khanacademy.org/math/statistics-probability'
),
(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Statistics',
    'StatQuest with Josh Starmer',
    'https://www.youtube.com/@statquest'
),
(
    (SELECT id FROM Courses WHERE code = 'STAT2012'),
    'Statistics and Probability',
    'Khan Academy - Statistics',
    'https://www.youtube.com/@khanacademy'
);

-- THE QUERY FOR EXTERNAL_LINKS FOR CORROSPONDING TABLES FOR THIRD YEAR COURSES

INSERT INTO external_link (course_id, topic, title, url) VALUES

-- =========================================================
-- 31. Software Engineering
-- CoSc3061
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3061'),
    'Software Engineering',
    'SWEBOK - Guide to the Software Engineering Body of Knowledge',
    'https://www.computer.org/education/bodies-of-knowledge/software-engineering'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3061'),
    'Software Development Life Cycle',
    'Atlassian - Software Development Life Cycle',
    'https://www.atlassian.com/software-development/software-development-life-cycle'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3061'),
    'Git and Version Control',
    'Git Documentation',
    'https://git-scm.com/doc'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3061'),
    'Software Engineering',
    'freeCodeCamp - Software Engineering',
    'https://www.youtube.com/@freecodecamp'
),


-- =========================================================
-- 32. Advanced Database Systems
-- CoSc3041
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3041'),
    'Advanced SQL',
    'PostgreSQL Documentation',
    'https://www.postgresql.org/docs/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3041'),
    'Database Indexing',
    'Use The Index, Luke',
    'https://use-the-index-luke.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3041'),
    'Database Systems',
    'CMU Database Group',
    'https://db.cs.cmu.edu/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3041'),
    'Database Systems',
    'Neso Academy - DBMS',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3041'),
    'SQL',
    'SQLBolt Interactive SQL',
    'https://sqlbolt.com/'
),


-- =========================================================
-- 33. Internet Programming I
-- CoSc3081
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'HTML',
    'MDN - HTML',
    'https://developer.mozilla.org/en-US/docs/Web/HTML'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'CSS',
    'MDN - CSS',
    'https://developer.mozilla.org/en-US/docs/Web/CSS'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'JavaScript',
    'MDN - JavaScript',
    'https://developer.mozilla.org/en-US/docs/Web/JavaScript'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'Web Development',
    'W3Schools Web Development',
    'https://www.w3schools.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'HTTP and Web',
    'MDN - How the Web Works',
    'https://developer.mozilla.org/en-US/docs/Learn_web_development/Getting_started/Web_standards/How_the_web_works'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3081'),
    'Web Development',
    'freeCodeCamp - Web Development',
    'https://www.youtube.com/@freecodecamp'
),


-- =========================================================
-- 34. Research Methods in Computer Science
-- CoSc3122
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3122'),
    'Research Methods',
    'MIT OpenCourseWare - How to Do Research',
    'https://ocw.mit.edu/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3122'),
    'Academic Research',
    'Google Scholar',
    'https://scholar.google.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3122'),
    'Research Papers',
    'arXiv',
    'https://arxiv.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3122'),
    'Academic Writing',
    'Purdue OWL - Research and Citation',
    'https://owl.purdue.edu/owl/research_and_citation/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3122'),
    'Research Methods',
    'SAGE Research Methods',
    'https://methods.sagepub.com/'
),


-- =========================================================
-- 35. Numerical Analysis
-- Math3081
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'Math3081'),
    'Numerical Analysis',
    'MIT OpenCourseWare - Introduction to Numerical Analysis',
    'https://ocw.mit.edu/courses/18-330-introduction-to-numerical-analysis-spring-2012/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math3081'),
    'Numerical Methods',
    'Numerical Analysis - Wikipedia',
    'https://en.wikipedia.org/wiki/Numerical_analysis'
),
(
    (SELECT id FROM Courses WHERE code = 'Math3081'),
    'Numerical Computing',
    'NumPy Documentation',
    'https://numpy.org/doc/'
),
(
    (SELECT id FROM Courses WHERE code = 'Math3081'),
    'Numerical Analysis',
    'MIT OpenCourseWare',
    'https://www.youtube.com/@mitocw'
),


-- =========================================================
-- 36. Introduction to Algorithms
-- CoSc3091
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3091'),
    'Algorithms',
    'MIT OpenCourseWare - Introduction to Algorithms',
    'https://ocw.mit.edu/courses/6-006-introduction-to-algorithms-spring-2020/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3091'),
    'Algorithm Design',
    'MIT OpenCourseWare - Introduction to Algorithms',
    'https://ocw.mit.edu/courses/6-046j-introduction-to-algorithms-sma-5503-fall-2005/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3091'),
    'Algorithm Visualization',
    'VisuAlgo',
    'https://visualgo.net/en'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3091'),
    'Algorithm Practice',
    'LeetCode',
    'https://leetcode.com/problemset/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3091'),
    'Algorithms',
    'MIT OpenCourseWare - Algorithms Lectures',
    'https://www.youtube.com/watch?v=ZA-tUyM_y7s'
),


-- =========================================================
-- 37. Operating Systems Principles and Design
-- CoSc3022
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3022'),
    'Operating Systems',
    'MIT OpenCourseWare - Operating System Engineering',
    'https://ocw.mit.edu/courses/6-828-operating-system-engineering-fall-2012/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3022'),
    'Operating Systems',
    'OSTEP - Operating Systems: Three Easy Pieces',
    'https://pages.cs.wisc.edu/~remzi/OSTEP/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3022'),
    'Operating Systems',
    'xv6 Operating System',
    'https://pdos.csail.mit.edu/6.828/2012/xv6.html'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3022'),
    'Operating Systems',
    'Neso Academy - Operating Systems',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3022'),
    'Operating Systems',
    'MIT OpenCourseWare - OS Engineering',
    'https://www.youtube.com/@mitocw'
),


-- =========================================================
-- 38. Rapid Application Development
-- CoSc3052
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3052'),
    'Rapid Application Development',
    'Microsoft Learn - .NET',
    'https://learn.microsoft.com/en-us/dotnet/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3052'),
    'Application Development',
    'Spring Documentation',
    'https://spring.io/projects/spring-framework'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3052'),
    'GUI Application Development',
    'JavaFX Documentation',
    'https://openjfx.io/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3052'),
    'Rapid Application Development',
    'freeCodeCamp - Application Development',
    'https://www.youtube.com/@freecodecamp'
),


-- =========================================================
-- 39. Introduction to Artificial Intelligence
-- CoSc3112
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3112'),
    'Artificial Intelligence',
    'Stanford CS221 - Artificial Intelligence',
    'https://web.stanford.edu/class/cs221/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3112'),
    'Artificial Intelligence',
    'Google Machine Learning Crash Course',
    'https://developers.google.com/machine-learning/crash-course'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3112'),
    'Machine Learning',
    'Kaggle Learn',
    'https://www.kaggle.com/learn'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3112'),
    'Artificial Intelligence',
    'Stanford Online',
    'https://www.youtube.com/@stanfordonline'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3112'),
    'Machine Learning',
    '3Blue1Brown - Neural Networks',
    'https://www.youtube.com/@3blue1brown'
),


-- =========================================================
-- 40. Selected Topics in Computer Science
-- CoSc4131
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4131'),
    'Computer Science Research',
    'arXiv Computer Science',
    'https://arxiv.org/list/cs/recent'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4131'),
    'Computer Science',
    'MIT OpenCourseWare - Electrical Engineering and Computer Science',
    'https://ocw.mit.edu/search/?d=Electrical%20Engineering%20and%20Computer%20Science'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4131'),
    'Computer Science Lectures',
    'MIT OpenCourseWare',
    'https://www.youtube.com/@mitocw'
),


-- =========================================================
-- 41. Automata and Complexity Theory
-- CoSc3102
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3102'),
    'Automata Theory',
    'MIT OpenCourseWare - Theory of Computation',
    'https://ocw.mit.edu/courses/18-404j-theory-of-computation-fall-2020/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3102'),
    'Automata and Complexity',
    'MIT OpenCourseWare - Automata, Computability and Complexity',
    'https://ocw.mit.edu/courses/6-045j-automata-computability-and-complexity-spring-2011/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3102'),
    'Theory of Computation',
    'GeeksforGeeks - Theory of Computation',
    'https://www.geeksforgeeks.org/theory-of-computation/introduction-of-theory-of-computation/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3102'),
    'Automata Theory',
    'Neso Academy - Theory of Computation',
    'https://www.youtube.com/@nesoacademy'
),


-- =========================================================
-- 42. Internet Programming II
-- CoSc3082
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'Advanced Web Development',
    'MDN Web APIs',
    'https://developer.mozilla.org/en-US/docs/Web/API'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'JavaScript',
    'MDN JavaScript Guide',
    'https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'REST APIs',
    'MDN - HTTP',
    'https://developer.mozilla.org/en-US/docs/Web/HTTP'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'Web Development',
    'W3Schools Web Development',
    'https://www.w3schools.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'Web Development',
    'freeCodeCamp - Web Development',
    'https://www.youtube.com/@freecodecamp'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3082'),
    'JavaScript',
    'JavaScript.info',
    'https://javascript.info/'
),


-- =========================================================
-- 43. Internship
-- CoSc3121
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc3121'),
    'Internship Preparation',
    'GitHub Skills',
    'https://skills.github.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3121'),
    'Version Control',
    'Git Documentation',
    'https://git-scm.com/doc'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3121'),
    'Programming Practice',
    'LeetCode',
    'https://leetcode.com/problemset/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3121'),
    'Developer Skills',
    'freeCodeCamp',
    'https://www.freecodecamp.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc3121'),
    'Software Development',
    'GitHub Skills - Introduction to GitHub',
    'https://www.youtube.com/@GitHub'
);

-- THE QUERY FOR EXTERNAL_LINKS FOR CORROSPONDING TABLES FOR SECOND YEAR COURSES

INSERT INTO external_link (course_id, topic, title, url) VALUES

-- =========================================================
-- 44. Comprehensive Degree Exit Exam I
-- CoSc4132
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4132'),
    'Ethiopian Computer Science Exit Exam',
    'EPrep - Ethiopian Computer Science Exit Exam Preparation',
    'https://www.eprep.et/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4132'),
    'Exit Exam Questions',
    'Ethiopian Computer Science Exit Exam Studio',
    'https://exitexamstudio.app/departments/computer-science'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4132'),
    'Exit Exam Questions',
    'Abren Endeg - Ethiopian Computer Science Exit Exam Questions',
    'https://abrenendeg.com/ethiopian-education/exit-exam/ethiopian-national-exit-exam-for-computer-science-model-questions-answers-pdf/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4132'),
    'Exit Exam Preparation',
    'Computer Science Exit Exam Overview - Code with Gaddisa',
    'https://www.youtube.com/watch?v=ikkVQ5_fRp8'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4132'),
    'Exit Exam Practice',
    'EthioExit - Ethiopian Exit Exam Resources',
    'https://www.ethioexit.com/'
),


-- =========================================================
-- 45. Senior Project I
-- CoSc4121
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4121'),
    'Project Management',
    'Atlassian - Project Management',
    'https://www.atlassian.com/work-management/project-management'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4121'),
    'Git and Version Control',
    'Git Documentation',
    'https://git-scm.com/doc'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4121'),
    'GitHub',
    'GitHub Skills',
    'https://skills.github.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4121'),
    'Software Engineering',
    'IEEE Software Engineering Body of Knowledge',
    'https://www.computer.org/education/bodies-of-knowledge/software-engineering'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4121'),
    'Software Project Development',
    'freeCodeCamp - Software Development',
    'https://www.youtube.com/@freecodecamp'
),


-- =========================================================
-- 46. Mobile Application Development
-- CoSc4081
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'Android Development',
    'Android Developers',
    'https://developer.android.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'Android Development',
    'Android Developers - Training',
    'https://developer.android.com/courses'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'Flutter',
    'Flutter Documentation',
    'https://docs.flutter.dev/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'React Native',
    'React Native Documentation',
    'https://reactnative.dev/docs/getting-started'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'Android Development',
    'Android Developers - YouTube',
    'https://www.youtube.com/@AndroidDevelopers'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4081'),
    'Flutter Development',
    'Flutter - YouTube',
    'https://www.youtube.com/@flutterdev'
),


-- =========================================================
-- 47. Computer and Network Security
-- CoSc4031
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Cybersecurity',
    'OWASP',
    'https://owasp.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Web Security',
    'OWASP Web Security Testing Guide',
    'https://owasp.org/www-project-web-security-testing-guide/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Cybersecurity Fundamentals',
    'Cisco Introduction to Cybersecurity',
    'https://www.netacad.com/courses/cybersecurity/introduction-cybersecurity'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Cybersecurity Practice',
    'TryHackMe',
    'https://tryhackme.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Cybersecurity',
    'Professor Messer - Security+',
    'https://www.youtube.com/@professormesser'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4031'),
    'Web Security',
    'PortSwigger Web Security Academy',
    'https://portswigger.net/web-security'
),


-- =========================================================
-- 48. Compiler Design
-- CoSc4101
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4101'),
    'Compiler Design',
    'GeeksforGeeks - Compiler Design',
    'https://www.geeksforgeeks.org/compiler-design-tutorials/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4101'),
    'Compiler Design',
    'GeeksforGeeks - Compiler Phases',
    'https://www.geeksforgeeks.org/compiler-design/phases-of-a-compiler/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4101'),
    'Compilers',
    'LLVM Documentation',
    'https://llvm.org/docs/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4101'),
    'Compiler Design',
    'Neso Academy - Compiler Design',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4101'),
    'Compilers',
    'LLVM - YouTube',
    'https://www.youtube.com/@LLVMProject'
),


-- =========================================================
-- 49. Computer Graphics
-- CoSc4072
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4072'),
    'Computer Graphics',
    'Scratchapixel - Computer Graphics',
    'https://www.scratchapixel.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4072'),
    'OpenGL',
    'Learn OpenGL',
    'https://learnopengl.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4072'),
    'Computer Graphics',
    'Khronos OpenGL Documentation',
    'https://www.khronos.org/opengl/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4072'),
    'Computer Graphics',
    'The Cherno - OpenGL',
    'https://www.youtube.com/@TheCherno'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4072'),
    'Computer Graphics',
    'Computerphile - Computer Graphics',
    'https://www.youtube.com/@Computerphile'
),


-- =========================================================
-- 50. Senior Project II
-- CoSc4122
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4122'),
    'Software Project',
    'GitHub Skills',
    'https://skills.github.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4122'),
    'Version Control',
    'Git Documentation',
    'https://git-scm.com/doc'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4122'),
    'Project Documentation',
    'Atlassian - Project Documentation',
    'https://www.atlassian.com/software/confluence'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4122'),
    'Software Engineering',
    'IEEE Software Engineering Body of Knowledge',
    'https://www.computer.org/education/bodies-of-knowledge/software-engineering'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4122'),
    'Software Development',
    'freeCodeCamp',
    'https://www.youtube.com/@freecodecamp'
),


-- =========================================================
-- 51. Wireless Communications and Mobile Computing
-- CoSc4032
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4032'),
    'Wireless Communications',
    'IEEE Communications Society',
    'https://www.comsoc.org/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4032'),
    'Wireless Networking',
    'Cisco Wireless',
    'https://www.cisco.com/c/en/us/solutions/enterprise-networks/wireless.html'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4032'),
    '5G Technology',
    'Ericsson Technology Review',
    'https://www.ericsson.com/en/reports-and-papers/ericsson-technology-review'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4032'),
    'Wireless Communications',
    'Neso Academy - Computer Networks',
    'https://www.youtube.com/@nesoacademy'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4032'),
    'Wireless Technology',
    'IEEE - YouTube',
    'https://www.youtube.com/@IEEEorg'
),


-- =========================================================
-- 52. Comprehensive Degree Exit Exam II
-- CoSc4133
-- =========================================================

(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Ethiopian Computer Science Exit Exam',
    'EPrep - Ethiopian Computer Science Exit Exam Preparation',
    'https://www.eprep.et/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Recent Exit Exam Questions',
    'Computer Science Exit Exam Practice - Exit Exam Studio',
    'https://exitexamstudio.app/departments/computer-science'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Ethiopian Exit Exam Resources',
    'EthioExit - Exit Exam Resources',
    'https://www.ethioexit.com/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Computer Science Exit Exam',
    'Abren Endeg - Exit Exam Questions and Answers',
    'https://abrenendeg.com/ethiopian-education/exit-exam/ethiopian-national-exit-exam-for-computer-science-model-questions-answers-pdf/'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Computer Science Exit Exam',
    'Computer Science Exit Exam Questions - June 2024',
    'https://www.cliffsnotes.com/study-notes/34552559'
),
(
    (SELECT id FROM Courses WHERE code = 'CoSc4133'),
    'Exit Exam Preparation',
    'Computer Science Exit Exam Questions and Answers',
    'https://www.youtube.com/watch?v=ikkVQ5_fRp8'
);

select * from courses;
select * from external_link;
select * from course_resources;