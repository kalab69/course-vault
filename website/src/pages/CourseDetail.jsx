import { useParams } from "react-router-dom";

function CourseDetail() {
    const { id } = useParams();

    return <h1>Course {id}</h1>;
}

export default CourseDetail;