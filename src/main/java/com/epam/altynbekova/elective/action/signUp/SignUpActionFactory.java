package com.epam.altynbekova.elective.action.signUp;

class SignUpActionFactory {
    public SignUpActionFactory() {
    }

    public StudentSignUpAction getStudentSignUpAction() {
        return new StudentSignUpAction();
    }

    public LecturerSignUpAction getLecturerSignUpAction() {
        return new LecturerSignUpAction();
    }
}
