import main.Card;
import main.Comparator;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class ShapeJude {


    Comparator comparator=new Comparator();
    List<Card> CommunityCard =new ArrayList<>();

    Player player=new Player("li");

    public static void main(String[] args) {
        ShapeJude shapeJude=new ShapeJude();
        shapeJude.HighCardTest();
    }


    public void HighCardTest()
    {
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));

        player.getHands().add(0,new Card(Card.Suit.HEARTS, Card.Rank.KING));
        player.getHands().add(0,new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        comparator.SetInfo(player,CommunityCard);
        comparator.CardJude();

        System.out.println(comparator.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }



}
