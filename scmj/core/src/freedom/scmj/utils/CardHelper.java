package freedom.scmj.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import freedom.scmj.entity.Card;


public class CardHelper {

	public static final List<Card> copyCard(List<Card> cards) {
		return new ArrayList<Card>(cards);
	}

	public static final boolean isPeng(List<Card> cards, Card card) {
		if (null == cards || card == null)
			throw new NullPointerException("cards or card is null");

		int count = 0;
		for (Card c : cards) {
			if (Card.isSame(c, card)) {
				count++;
				if (count == 2)
					break;
			}
		}

		return count >= 2;
	}

	public static final boolean isGang(List<Card> cards, Card card) {
		if (null == cards || card == null)
			throw new NullPointerException("cards or card is null");

		int count = 0;
		for (Card c : cards) {
			if (Card.isSame(c, card)) {
				count++;
				if (count == 3)
					break;
			}
		}

		return count >= 3;
	}

	public static final boolean isHu(List<Card> cards, Card card) {
		if (null == cards || card == null)
			throw new NullPointerException("cards or card is null");
		List<Card> temp = new ArrayList<Card>(cards);
		temp.add(card);

		Collections.sort(temp);
		int count = temp.size();

		if (count == 2)
			return Card.isSame(temp.get(0), temp.get(1));
		else {
			if (count == 14 && checkDoubleModel(temp))
				return true;
			else {
				int doubleIndex = -1;
				while (doubleIndex + 1 < count) {
					// goto check
					List<Card> checkList = copyCard(temp);
					// check double
					if ((doubleIndex = checkDouble(checkList, doubleIndex + 1)) > -1) {
						if (checkAllLinked(checkList))
							return true;
					} else
						return false;
				}
			}
		}

		return false;
	}

	/**
	 * 7对判断
	 * 
	 * @param temp
	 * @return
	 */
	private static final boolean checkDoubleModel(List<Card> temp) {
		for (int i = 0; i < 7; i++) {
			Card c1 = temp.get(i * 2);
			Card c2 = temp.get(i * 2 + 1);
			if (!Card.isSame(c1, c2))
				return false;
		}

		return true;
	}

	/**
	 * 是否全部可以成3张/3连
	 * 
	 * @param checkList
	 * @return
	 */
	private static final boolean checkAllLinked(List<Card> checkList) {
		while (checkList.size() > 0) {
			if (!checkLinked(checkList))
				break;
		}

		if (checkList.size() == 0) {
			System.out.println("success hu");
			return true;
		}

		return false;
	}

	/**
	 * 检查是否有将
	 * 
	 * @param checkList
	 * @return
	 */
	private static final int checkDouble(List<Card> checkList, int firstIndex) {
		boolean hasDouble = false;
		int doubleIndex = -1;
		int count = checkList.size();
		Card firstCard = checkList.get(firstIndex);
		Card secondCard = null;
		for (int i = firstIndex + 1; i < count; i++) {
			secondCard = checkList.get(i);
			if (Card.isSame(firstCard, secondCard)) {
				hasDouble = true;
				doubleIndex = i;
				break;
			}
			firstCard = secondCard;
		}

		// remove double card
		if (hasDouble) {
			checkList.remove(firstCard);
			checkList.remove(secondCard);
		}

		return doubleIndex;
	}

	/**
	 * 判断是否有3张/3连
	 * */
	private static final boolean checkLinked(List<Card> checkList) {
		boolean hasLink = false;
		// 3张一组进行判断
		Card c1 = checkList.get(0);
		Card c2 = checkList.get(1);
		Card c3 = checkList.get(2);
		if (Card.isSame(c1, c2) && Card.isSame(c2, c3)) {
			// 三张
			checkList.remove(c1);
			checkList.remove(c2);
			checkList.remove(c3);
			hasLink = true;
		} else {
			Card s1 = checkList.get(0), s2 = null, s3 = null, checkCard = null;
			for (int i = 1; i < checkList.size(); i++) {
				checkCard = checkList.get(i);
				if (null == s2 && Card.isSameType(s1, checkCard)
						&& checkCard.getValue() - s1.getValue() == 1) {
					s2 = checkCard;
				} else if (null == s3 && Card.isSameType(s1, checkCard)
						&& checkCard.getValue() - s1.getValue() == 2) {
					s3 = checkCard;
				} else if (!Card.isSameType(s1, checkCard)
						|| checkCard.getValue() - s1.getValue() > 2) {
					break;
				}

				if (s2 != null && s3 != null) {
					hasLink = true;
					break;
				}
			}

			if (hasLink) {
				checkList.remove(s1);
				checkList.remove(s2);
				checkList.remove(s3);
			}
		}
		return hasLink;
	}
}
