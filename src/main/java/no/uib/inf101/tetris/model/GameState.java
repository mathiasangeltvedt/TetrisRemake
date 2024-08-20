package no.uib.inf101.tetris.model;

/**
 * Denne klassen brukes for å hente ut statusen til spillet.
 * De forskjellige statusene som er implementert foreløpig
 * er de som man ser under. Disse brukes for å sjekke hva som skal skrives ut
 * på skjermen/tegnes på skjermen, og hva som skal skje i spillet.
 */
public enum GameState {
    ACTIVE_GAME(), GAME_OVER(), WELCOME(), PAUSED(), CONTROLS()
}
