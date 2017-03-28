package freedom.scmj.common.utils;

import java.io.InputStream;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XMLHelper {

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		XmlReader reader = new XmlReader();
		InputStream in =XMLHelper.class.getClassLoader().getSystemResourceAsStream("cards.xml");
		Element root = reader.parse(in);
		String rootName = root.getName();
		System.out.println("rootName = " + rootName);
		Array<Element> cards = root.getChildByName("Cards").getChildrenByName("Card");
		for (Element element : cards) {
			System.out.println(element.getAttribute("Name"));
		}
	}
}
