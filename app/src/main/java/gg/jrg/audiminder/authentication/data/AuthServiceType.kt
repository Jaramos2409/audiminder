package gg.jrg.audiminder.authentication.data

/**
 * In the future the app will have the ability to use other services besides Spotify,
 * for now only Spotify is being implemented as a proof of concept.
 */
enum class AuthServiceType {
    SPOTIFY,
    TIDAL,
    APPLE
}
