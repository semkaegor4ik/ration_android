package com.example.ration;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ration.model.NormalRecipe;
import com.example.ration.model.RecipeType;
import com.example.ration.model.User;
import com.example.ration.model.Week;
import com.example.ration.service.RestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RationActivity extends AppCompatActivity {
    private final Map<Week, Map<RecipeType, NormalRecipe>> recipes = new HashMap<>();
    private static final String FILE_NAME = "recipes.ser";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ration);

        try(ObjectInputStream os = new ObjectInputStream(this.openFileInput(FILE_NAME))) {
            recipes.putAll((Map<Week, Map<RecipeType, NormalRecipe>>) os.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User(getIntent().getParcelableExtra("user"),
                getIntent().getStringExtra("gender"),
                getIntent().getStringExtra("activity"));

        Button generateRationButton = findViewById(R.id.generateRationButton);
        Button getRecipesButton = findViewById(R.id.getWeekReceipsButton);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        generateRationButton.setOnClickListener(v -> serializeAndSave(null)/*{
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET,
                    RestService.URL,
                    null,
                    this::serializeAndSave,
                    e -> System.out.println(e.toString()));
            requestQueue.add(objectRequest);
        }*/);

        getRecipesButton.setOnClickListener(v -> System.out.println(recipes));

    }

    private void serializeAndSave(JSONObject readValue) {

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<Week, Map<RecipeType, NormalRecipe>>> typeRef = new TypeReference<>() {};

        try {
            recipes.putAll(objectMapper.readValue("{\n" +
                    "    \"TUESDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Сёмга на пару с рисом\",\n" +
                    "            \"description\": \"Промываем стейк семги водой, готовим на пару 30 минут без добавления соли. Рис отварить в подсолённой воде.\",\n" +
                    "            \"products\": {\n" +
                    "                \"сёмга\": 353,\n" +
                    "                \"рис\": 75\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Бутерброды с ветчиной\",\n" +
                    "            \"description\": \"Обжарить хлеб с двух сторон, предварительно обмакнув его в яичной смеси. Сверху положить ветчину. При подаче украсить зеленью.\",\n" +
                    "            \"products\": {\n" +
                    "                \"яйцо\": 100,\n" +
                    "                \"ветчина\": 43,\n" +
                    "                \"цельнозерновой хлеб\": 111\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриные ножки в духовке с картофельным пюре\",\n" +
                    "            \"description\": \"Куриные ножки замариновать в специях и оставить на 30 минут. При температуре 200 градусов запечь ножки в духовке до готовности. Картофель отварить и потолочь в пюре. Подавать с овощным салатом из любимых овощей.\",\n" +
                    "            \"products\": {\n" +
                    "                \"помидор\": 131,\n" +
                    "                \"куриные ножки\": 282,\n" +
                    "                \"картофель\": 137,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"THURSDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \" Куриные котлеты с булгуром\",\n" +
                    "            \"description\": \"Куриное филе пропустить через мясорубку или блендер. Также измельчить брокколи. Смешать куриное филе с брокколи, добавить соль, специи и яйцо и хорошо перемешать. Сформировать котлеты и обжарить с двух сторон на антипригарной сковороде без масла. Булгур отварить в подсолённой воде.\",\n" +
                    "            \"products\": {\n" +
                    "                \"яйцо\": 53,\n" +
                    "                \"брокколи \": 182,\n" +
                    "                \"куриное филе\": 299,\n" +
                    "                \"булгур\": 73\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Каша с яблоком\",\n" +
                    "            \"description\": \"Отварить кашу, яблоко порезать кубиками, сверху присыпать корицей. \",\n" +
                    "            \"products\": {\n" +
                    "                \"яблоко\": 229,\n" +
                    "                \"овсяная крупа\": 152\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриное филе на гриле с овощным салатом\",\n" +
                    "            \"description\": \"Куриное филе замариновать в любимых специях (без добавления масла) и обжарить 3-4 минуты в электрогриле. Для овощного салата смешать любые свежие овощи.\",\n" +
                    "            \"products\": {\n" +
                    "                \"помидор\": 196,\n" +
                    "                \"куриное филе\": 327,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"SUNDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Куриное филе на гриле с гречкой\",\n" +
                    "            \"description\": \"Куриное филе замариновать в любимых специях (без добавления масла) и обжарить 3-4 минуты в электрогриле. Гречку отварить в подсоленной воде.\",\n" +
                    "            \"products\": {\n" +
                    "                \"гречка\": 69,\n" +
                    "                \"куриное филе\": 419\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Бутерброды с ветчиной\",\n" +
                    "            \"description\": \"Обжарить хлеб с двух сторон, предварительно обмакнув его в яичной смеси. Сверху положить ветчину. При подаче украсить зеленью.\",\n" +
                    "            \"products\": {\n" +
                    "                \"яйцо\": 100,\n" +
                    "                \"ветчина\": 43,\n" +
                    "                \"цельнозерновой хлеб\": 111\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриное филе на гриле с овощным салатом\",\n" +
                    "            \"description\": \"Куриное филе замариновать в любимых специях (без добавления масла) и обжарить 3-4 минуты в электрогриле. Для овощного салата смешать любые свежие овощи.\",\n" +
                    "            \"products\": {\n" +
                    "                \"помидор\": 196,\n" +
                    "                \"куриное филе\": 327,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"MONDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Тушеная капуста с курицей\",\n" +
                    "            \"description\": \"Капусту нашинковать соломкой, филе нарезать небольшими кусочками. Обжарить курицу на сухой сковороде. Затем выложить капусту в сковороду, добавить немного воды и томатную пасту (без сахара). Тушить на медленном огне до полуготовности. Затем добавить нашинкованные соломкой лук и морковь. Тушить до готовности.  При подаче посыпать зеленью. \",\n" +
                    "            \"products\": {\n" +
                    "                \"капуста белокочанная\": 621,\n" +
                    "                \"лук репчатый\": 204,\n" +
                    "                \"куриное филе\": 359,\n" +
                    "                \"морковь\": 262\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Каша с яблоком\",\n" +
                    "            \"description\": \"Отварить кашу, яблоко порезать кубиками, сверху присыпать корицей. \",\n" +
                    "            \"products\": {\n" +
                    "                \"яблоко\": 229,\n" +
                    "                \"овсяная крупа\": 152\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриные ножки в духовке с картофельным пюре\",\n" +
                    "            \"description\": \"Куриные ножки замариновать в специях и оставить на 30 минут. При температуре 200 градусов запечь ножки в духовке до готовности. Картофель отварить и потолочь в пюре. Подавать с овощным салатом из любимых овощей.\",\n" +
                    "            \"products\": {\n" +
                    "                \"помидор\": 131,\n" +
                    "                \"куриные ножки\": 282,\n" +
                    "                \"картофель\": 137,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"FRIDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Куриные сердечки с овощами и гречкой\",\n" +
                    "            \"description\": \"Сердечки очистить от пленок и замочить в холодной воде на 20 мин. Лук порезать кольцами, чеснок измельчить. Слить воду с сердечек и вместе с луком обжарить на сухой сковороде. Затем добавить овощи, чеснок, воль, перец. Тушить до готовности. Гречку отварить в подсоленой воде.\",\n" +
                    "            \"products\": {\n" +
                    "                \"лук репчатый\": 102,\n" +
                    "                \"гречка\": 69,\n" +
                    "                \"куриные сердечки\": 279,\n" +
                    "                \"брокколи \": 182,\n" +
                    "                \"стручковая фасоль\": 135\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Пшённая каша с ягодами\",\n" +
                    "            \"description\": \"Отварить кашу с молоком или водой на медленном огне. Украсить ягодами при подаче.\",\n" +
                    "            \"products\": {\n" +
                    "                \"пшено\": 142,\n" +
                    "                \"ягода\": 87\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриные сердечки с овощами\",\n" +
                    "            \"description\": \"Сердечки очистить от пленок и замочить в холодной воде на 20 мин. Лук порезать кольцами, чеснок измельчить. Слить воду с сердечек и вместе с луком обжарить на сухой сковороде. Затем добавить овощи, чеснок, воль, перец. Тушить до готовности.\",\n" +
                    "            \"products\": {\n" +
                    "                \"лук репчатый\": 63,\n" +
                    "                \"куриные сердечки\": 262,\n" +
                    "                \"брокколи \": 170,\n" +
                    "                \"стручковая фасоль\": 84\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"SATURDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Сёмга на пару с рисом\",\n" +
                    "            \"description\": \"Промываем стейк семги водой, готовим на пару 30 минут без добавления соли. Рис отварить в подсолённой воде.\",\n" +
                    "            \"products\": {\n" +
                    "                \"сёмга\": 353,\n" +
                    "                \"рис\": 75\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Овсяноблин\",\n" +
                    "            \"description\": \"Взбить вилкой, влить на раскалённую сковороду, накрыть крышкой. Когда блин схватится, перевернуть. Намазать тонком слоем творожного сыра, нарезать кружочками огурец, сложить блин пополам, получается, как сэндвич.\",\n" +
                    "            \"products\": {\n" +
                    "                \"творожный сыр\": 22,\n" +
                    "                \"яйцо\": 83,\n" +
                    "                \"овсяная крупа\": 122,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриные сердечки с овощами\",\n" +
                    "            \"description\": \"Сердечки очистить от пленок и замочить в холодной воде на 20 мин. Лук порезать кольцами, чеснок измельчить. Слить воду с сердечек и вместе с луком обжарить на сухой сковороде. Затем добавить овощи, чеснок, воль, перец. Тушить до готовности.\",\n" +
                    "            \"products\": {\n" +
                    "                \"лук репчатый\": 63,\n" +
                    "                \"куриные сердечки\": 262,\n" +
                    "                \"брокколи \": 170,\n" +
                    "                \"стручковая фасоль\": 84\n" +
                    "            }\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"WEDNESDAY\": {\n" +
                    "        \"LUNCH\": {\n" +
                    "            \"name\": \"Тушеная капуста с курицей\",\n" +
                    "            \"description\": \"Капусту нашинковать соломкой, филе нарезать небольшими кусочками. Обжарить курицу на сухой сковороде. Затем выложить капусту в сковороду, добавить немного воды и томатную пасту (без сахара). Тушить на медленном огне до полуготовности. Затем добавить нашинкованные соломкой лук и морковь. Тушить до готовности.  При подаче посыпать зеленью. \",\n" +
                    "            \"products\": {\n" +
                    "                \"капуста белокочанная\": 621,\n" +
                    "                \"лук репчатый\": 204,\n" +
                    "                \"куриное филе\": 359,\n" +
                    "                \"морковь\": 262\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"BREAKFAST\": {\n" +
                    "            \"name\": \"Бутерброды с ветчиной\",\n" +
                    "            \"description\": \"Обжарить хлеб с двух сторон, предварительно обмакнув его в яичной смеси. Сверху положить ветчину. При подаче украсить зеленью.\",\n" +
                    "            \"products\": {\n" +
                    "                \"яйцо\": 100,\n" +
                    "                \"ветчина\": 43,\n" +
                    "                \"цельнозерновой хлеб\": 111\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"DINNER\": {\n" +
                    "            \"name\": \"Куриное филе на гриле с овощным салатом\",\n" +
                    "            \"description\": \"Куриное филе замариновать в любимых специях (без добавления масла) и обжарить 3-4 минуты в электрогриле. Для овощного салата смешать любые свежие овощи.\",\n" +
                    "            \"products\": {\n" +
                    "                \"помидор\": 196,\n" +
                    "                \"куриное филе\": 327,\n" +
                    "                \"огурец\": 174\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}", typeRef));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream os = new ObjectOutputStream(this.openFileOutput(new File(FILE_NAME).getName(), Context.MODE_PRIVATE))) {
            os.writeObject(recipes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}