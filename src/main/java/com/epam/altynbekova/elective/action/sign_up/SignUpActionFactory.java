package com.epam.altynbekova.elective.action.sign_up;

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
