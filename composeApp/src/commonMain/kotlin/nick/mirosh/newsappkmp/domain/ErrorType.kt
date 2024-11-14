package nick.mirosh.newsapp.domain

/**
 * Represents different types of errors that can occur in the application.
 *
 * This is a sealed class, which allows us to define a limited set of error types that can be used throughout the app.
 */
sealed class ErrorType {
    /** Represents an unauthorized error. */
    data object Unauthorized : ErrorType()

    /** Represents a not found error. */
    data object NotFound : ErrorType()

    /** Represents a general error. */
    data object General : ErrorType()

    /** Represents an unknown error. */
    data object Unknown : ErrorType()
}
