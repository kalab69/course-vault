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

select * from courses;
select * from external_link;
select * from course_resources;