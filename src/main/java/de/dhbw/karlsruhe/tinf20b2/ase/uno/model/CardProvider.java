package de.dhbw.karlsruhe.tinf20b2.ase.uno.model;

import de.dhbw.karlsruhe.tinf20b2.ase.uno.model.domain.Card;

import java.util.List;

public interface CardProvider {

    List<Card> listAllCards();
}
