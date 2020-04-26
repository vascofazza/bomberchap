package bomberman.core;

import static playn.core.PlayN.assets;

import java.util.HashMap;
import java.util.Map;

/**
 * The PropertiesManager class is responsible of the String localization. It loads and parse the
 * game.propriets file from the assets/text sub directory using the native {@link playn.core.PlayN.assets} library. 
 * It's mainly used to localize the game resources relative path.
 * 
 * @author Federico Scozzafava
 */
public class PropertiesManager
{
	
	/**
	 * The singleton pattern.
	 */
	private static PropertiesManager instance;

	/**
	 * Gets the single instance of PropertiesManager.
	 * 
	 * @return single instance of PropertiesManage
	 */
	public static PropertiesManager getInstance()
	{
		if (instance == null) instance = new PropertiesManager();
		return instance;
	}

	/**
	 * Gets the parameter.
	 * 
	 * @param name
	 *            the parameter's name
	 * @return the parameter
	 */
	public static String getParameter(String name)
	{
		return getInstance().settings.get(name);
	}

	/**
	 * This map contains the values from the parsed document.
	 */
	private Map<String, String> settings = new HashMap<String, String>();

	/**
	 * Instantiates a new properties manager.
	 */
	private PropertiesManager()
	{
		try
		{
			String[] config = assets().getTextSync("texts/game.properties")
					.split("\n");
			for (String x : config)
			{
				String[] temp = x.trim().split("=");
				settings.put(temp[0], temp[1]);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
