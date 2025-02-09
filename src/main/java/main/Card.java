package main;

public class Card implements Comparable<Card>{


    @Override
    public int compareTo(Card OtherCard) {
        return OtherCard.rank.getValue()-this.rank.getValue();
    }

    public enum Suit {

        SPADES(0,"♠"),
        HEARTS(1,"♥"),
        DIAMONDS(2,"♦"),
        CLUBS(3,"♣");

        private final int value;

        private final String displayName;

        // Constructor
        private Suit(int value,String displayName) {
            this.value = value;
            this.displayName=displayName;
        }

        // Method to get the numeric value
        public int getValue() {
            return value;
        }

        public String getDisplayName(){
            return displayName;
        }

        public static Suit fromValue(int value) {
            for (Suit suit : Suit.values()) {
                if (suit.getValue() == value) {
                    return suit;
                }
            }
            throw new IllegalArgumentException("Invalid suit value: " + value);
        }
    }

    public enum Rank {
        TWO(2, "2"),
        THREE(3, "3"),
        FOUR(4, "4"),
        FIVE(5, "5"),
        SIX(6, "6"),
        SEVEN(7, "7"),
        EIGHT(8, "8"),
        NINE(9, "9"),
        TEN(10, "10"),
        JACK(11, "J"),
        QUEEN(12, "Q"),
        KING(13, "K"),
        ACE(14, "A"); // ACE is set to 14 so that it ranks higher than KING in comparisons

        private final int value;
        private final String displayName;

        Rank(int value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }

        public int getValue() {
            return value;
        }

        public String getDisplayName() {
            return displayName;
        }

        // Get the corresponding Rank based on the numeric value
        public static Rank fromValue(int value) {
            for (Rank rank : Rank.values()) {
                if (rank.getValue() == value) {
                    return rank;
                }
            }
            throw new IllegalArgumentException("Invalid card value: " + value);
        }
    }


    Suit suit;
    Rank rank;

    private String imagePath;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }


    @Override
    public String toString() {
        return suit.getDisplayName()+rank.getDisplayName();
    }
}
