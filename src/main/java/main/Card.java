package main;

public class Card {



    public enum Suit
    {

        SPADES(0),
        HEARTS(1),
        DIAMONDS(2),
        CLUBS(3);

        private final int value;

        // Constructor
        private Suit(int value) {
            this.value = value;
        }

        // Method to get the numeric value
        public int getValue() {
            return value;
        }

    }

    public enum Rank
    {

        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14); // ACE is set to 14 so that it ranks higher than KING in comparisons


        private final int value;

        Rank(int value) {
            this.value=value;
        }

        public int getValue() {
            return value;
        }
    }


    private Suit suit;
    private Rank rank;

    private String imagePath;

    public Card(Suit suit,Rank rank)
    {
        this.suit=suit;
        this.rank=rank;
    }





}
