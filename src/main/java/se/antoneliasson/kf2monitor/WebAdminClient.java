package se.antoneliasson.kf2monitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WebAdminClient {
    private final String url;
    private final String user;
    private final String password;

    public WebAdminClient(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Map<String, String> mapDL(Elements es) {
        Map<String, String> m = new HashMap<>();
        if (!es.isEmpty() && es.size() % 2 == 0) {
            for (int i = 0; i < es.size(); i += 2) {
                m.put(es.get(i).text(), es.get(i+1).text());
            }
        }
        return m;
    }

    public GameDataContainer update() {
        Document doc = null;

        String authString = user + ":" + password;
        String encodedString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));

        try {
            doc = Jsoup.connect(url)
                    .header("Authorization", "Basic " + encodedString)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        File input = new File("serverinfo.html");
//        try {
//            doc = Jsoup.parse(input, "UTF-8", url);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Element currentGame = doc.getElementById("currentGame");
        Elements dlItems = currentGame.select("dt, dd");
        Map<String, String> game = mapDL(dlItems);

        Element currentRules = doc.getElementById("currentRules");
        Elements items = currentRules.select("dt, dd");
        Map<String, String> rules = mapDL(items);

        Element playersTable = doc.getElementById("players");
        Elements tableHeader = playersTable.select("table thead tr th");

        // columns = map text columnNames
        List<String> columnNames = new ArrayList<>();
        for (Element th : tableHeader) {
            // NOTE: inside the equals string is a magic non-breaking space character
            if (th.text().equals("\u00A0"))
                continue;
            columnNames.add(th.text());
        }

        Elements playerRows = playersTable.select("table tbody tr");
        List<Player> players = new ArrayList<>();
        for (Element row : playerRows) {
            Elements cells = row.select("tr td");
            // playerFields = map text cells
            List<String> playerFields = new ArrayList<>();
            for (Element td : cells) {
                // NOTE: inside the equals string is a magic non-breaking space character
                if (td.text().equals("\u00A0"))
                    continue;
                playerFields.add(td.text());
            }

            Map<String, String> fieldMap = new HashMap<>();
            // fieldMap = zip columnNames playerFields
            Iterator<String> keyIt = columnNames.iterator();
            Iterator<String> valIt = playerFields.iterator();
            while (keyIt.hasNext() && valIt.hasNext()) {
                fieldMap.put(keyIt.next(), valIt.next());
            }
            Player player = new Player(fieldMap);
            players.add(player);
        }

        // Players are sorted by dosh. Sort them by kills:
        players.sort(new PlayerComparator());

        return new GameDataContainer(game, rules, players);
    }
}
