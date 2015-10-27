package com.aidanogrady.abacus.model;

/**
 * For each code smell, a rating is given to each one. A good rating means the
 * class/method is fine and needs no changed. A dodgy rating means the class
 * should be looked at, but not a major issue (essentially edge cases where it
 * needs to be the case). A bad rating means "CHANGE NOW!".
 */
public enum Rating {
    GOOD {
        @Override
        public String toString() {
            return "Good";
        }
    },
    BAD {
        @Override
        public String toString() {
            return "Bad";
        }
    },
    DODGY {
        @Override
        public String toString() {
            return "Okay";
        }
    }
}
