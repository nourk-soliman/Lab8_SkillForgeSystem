/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.course;

import model.user.Student;

public class CertificateService {
    
    public static boolean generateCertificateIfCompleted(Student student, Course course) {
        // 1. Find course progress for this course
        CourseProgress progress = null;
        for (CourseProgress cp : student.getProgress()) {
            if (cp.getCourseId().equals(course.getCourseId())) {
                progress = cp;
                break;
            }
        }
        if (progress == null) return false;
        
        // 2. Update completion status using existing method
        progress.UpdateComplete(progress.getLessonProgress());
        
        // 3. Check if certificate can be generated using Certificate class method
        if (!Certificate.canGenerateCertificate(progress, course)) {
            return false;
        }
        
        // 4. Check if certificate already exists
        for (Certificate cert : student.getCertificates()) {
            if (cert.getCourseId().equals(course.getCourseId())) {
                return false; // Already has certificate
            }
        }
        
        // 5. Generate certificate ID and create certificate
        String certId = "CERT_" + course.getCourseId() + "_" + student.getUserId() + "_" + System.currentTimeMillis();
        Certificate certificate = new Certificate(certId, student.getUserId(), course.getCourseId());
        student.getCertificates().add(certificate);
        
        return true;
    }
}
