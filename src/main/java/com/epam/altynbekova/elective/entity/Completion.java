package com.epam.altynbekova.elective.entity;

public class Completion extends BaseEntity {
        private boolean isComplete;
        private byte grade;
        private String feedback;

        public Completion() {
        }

        public Completion(boolean isComplete, byte grade, String feedback) {
            this.isComplete = isComplete;
            this.grade = grade;
            this.feedback = feedback;
        }

        public boolean isComplete() {
            return isComplete;
        }

        public void setComplete(boolean complete) {
            isComplete = complete;
        }

        public byte getGrade() {
            return grade;
        }

        public void setGrade(byte grade) {
            this.grade = grade;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

    @Override
    public String toString() {
        return "Completion{" +
                "isComplete=" + isComplete +
                ", grade=" + grade +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
