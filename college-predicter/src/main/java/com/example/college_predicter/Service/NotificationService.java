//package com.example.college_predicter.Service;
//
//import com.example.college_predicter.Entity.Notification;
//import com.example.college_predicter.Reposetry.NotificationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class NotificationService {
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    // Save the notification to the database
//    public void saveNotification(Notification notification) {
//        if (notification != null) {
//            notificationRepository.save(notification);
//        }
//    }
//
//    // Get all notifications for a specific college
//    public List<Notification> getNotificationsByCollegeId(long collegeId) {
//        return notificationRepository.findByCollegeId(collegeId);
//    }
//}
