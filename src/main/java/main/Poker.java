package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Poker {

    private final List<Card> cards;//All PokerDeck


    public Poker()
    {
        cards=new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            for (int j = 2; j <=14; j++) {
                cards.add(new Card(Card.Suit.fromValue(i), Card.Rank.fromValue(j)));
            }
        }
        shuffleCards();

    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffleCards() {
        Collections.shuffle(cards); // Shuffle the deck randomly
    }




}
