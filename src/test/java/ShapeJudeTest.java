import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class ShapeJudeTest {


    main.ShapeJude shapeJude =new main.ShapeJude();
    List<Card> CommunityCard =new ArrayList<>();
    Player player=new Player("li");

    public static void main(String[] args) {
        ShapeJudeTest shapeJudeTest =new ShapeJudeTest();
        shapeJudeTest.HighCardTest();
        System.out.println("-----------------------------------");
        shapeJudeTest.OnePairTest();
        System.out.println("-----------------------------------");
        shapeJudeTest.TwoPairTest();
        System.out.println("-----------------------------------");
        shapeJudeTest.ThreeKindTest();
    }

    public void Clear()
    {
        CommunityCard.clear();
        player.InitPlayer();

    }


    public void HighCardTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));


        player.getHands().add(0,new Card(Card.Suit.HEARTS, Card.Rank.KING));
        player.getHands().add(0,new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }


    public void OnePairTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.EIGHT));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));


        player.getHands().add(0,new Card(Card.Suit.HEARTS, Card.Rank.KING));
        player.getHands().add(0,new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }

    public void TwoPairTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.KING));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.JACK));


        player.getHands().add(0,new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        player.getHands().add(0,new Card(Card.Suit.CLUBS, Card.Rank.FOUR));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }

    public void ThreeKindTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.TWO));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));


        player.getHands().add(0,new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        player.getHands().add(0,new Card(Card.Suit.CLUBS, Card.Rank.FIVE));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }



}
