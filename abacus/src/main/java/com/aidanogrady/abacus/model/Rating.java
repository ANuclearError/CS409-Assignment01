package com.aidanogrady.abacus.model;

/**
 * Each code smell is given a rating based on the analysis of the class. There
 * are three possible ratings.
 *
 * <ul>
 *     <li><b>Good</b> - There are no problems here</li>
 *     <li><b>Okay</b> - You are close to encountering problems</li>
 *     <li><b>Good</b> - Refactor now for the love of god</li>
 * </ul>
 *
 * @author Aidan O'Grady
 * @since 0.3
 */
public enum  Rating {
    /**
     * The code smell analysis does not feel there is an issue
     */
    GOOD,

    /**
     * The code smell analysis feels you are close to encountering issues.
     */
    OKAY,

    /**
     * The code smell analysis feels this must change as soon as possible.
     */
    BAD
}
