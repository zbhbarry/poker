import main.AiPlayer;
import main.Card;
import main.Player;

import java.util.ArrayList;
import java.util.List;

public class ShapeJudeTest {


    main.ShapeJude shapeJude =new main.ShapeJude();
    List<Card> CommunityCard =new ArrayList<>();
    Player player=new AiPlayer("li");

    public static void main(String[] args) {

        ShapeJudeTest shapeJudeTest =new ShapeJudeTest();
        shapeJudeTest.HighCardTest();
        sep();
        shapeJudeTest.OnePairTest();
        sep();
        shapeJudeTest.TwoPairTest();
        sep();
        shapeJudeTest.ThreeKindTest();
        sep();
        shapeJudeTest.StraightTest();
        sep();
        shapeJudeTest.FlushTest();
        sep();
        shapeJudeTest.FullHouseTest();
        sep();
        shapeJudeTest.FourKindTest();
        sep();
        shapeJudeTest.StraightFlushTest();

    }

    public static void sep()
    {
        System.out.println("-----------------------------------");
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


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        player.getHands().add(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

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


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.KING));
        player.getHands().add(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN));

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


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.EIGHT));
        player.getHands().add(new Card(Card.Suit.CLUBS, Card.Rank.FOUR));

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


        player.getHands().add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        player.getHands().add(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }


    public void StraightTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.SIX));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        player.getHands().add(new Card(Card.Suit.CLUBS, Card.Rank.FOUR));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }


    public void FlushTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.SEVEN));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.SIX));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.FOUR));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }

    public void FullHouseTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.FIVE));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.FIVE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));


        player.getHands().add(new Card(Card.Suit.SPADES, Card.Rank.NINE));
        player.getHands().add(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }

    public void FourKindTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.SPADES, Card.Rank.KING));
        CommunityCard.add(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.CLUBS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));


        player.getHands().add(new Card(Card.Suit.SPADES, Card.Rank.ACE));
        player.getHands().add(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }

    public void StraightFlushTest()
    {

        Clear();
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.FIVE));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.FOUR));
        CommunityCard.add(new Card(Card.Suit.HEARTS, Card.Rank.TEN));


        player.getHands().add(new Card(Card.Suit.HEARTS, Card.Rank.THREE));
        player.getHands().add(new Card(Card.Suit.DIAMONDS, Card.Rank.FIVE));

        shapeJude.SetInfo(player,CommunityCard);
        shapeJude.CardJude();

        System.out.println(shapeJude.getAllCard());
        System.out.println(player.getMaxShapeCards());
        System.out.println(player.getShape());
    }



}
