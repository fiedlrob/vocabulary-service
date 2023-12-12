package com.raftec.palabrita.vocabularyservice;

import com.raftec.palabrita.vocabularyservice.domain.model.Collection;
import com.raftec.palabrita.vocabularyservice.domain.model.Language;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataProvider {
    public static List<Collection> getCollections() {
        return new ArrayList<>(
                List.of(
                        new Collection(
                                100L,
                                TestConstants.UserId1,
                                TestConstants.CollectionId1,
                                TestConstants.CreationDate1,
                                TestConstants.CollectionName1,
                                new Language("en", "English", "English"),
                                new Language("es", "Spanish", "Español")
                        ),
                        new Collection(
                                100L,
                                TestConstants.UserId1,
                                TestConstants.CollectionId2,
                                TestConstants.CreationDate2,
                                TestConstants.CollectionName2,
                                new Language("en", "English", "English"),
                                new Language("es", "Spanish", "Español")
                        ),
                        new Collection(
                                100L,
                                TestConstants.UserId2,
                                TestConstants.CollectionId3,
                                TestConstants.CreationDate3,
                                TestConstants.CollectionName3,
                                new Language("en", "English", "English"),
                                new Language("es", "Spanish", "Español")
                        )
                )
        );
    }

    public static List<Language> getLanguages() {
        return new ArrayList<>(
                Arrays.asList(
                        new Language("af", "Afrikaans", "Afrikaans"),
                        new Language("am", "Amharic", "አማርኛ"),
                        new Language("ar", "Arabic", "العربية"),
                        new Language("az", "Azerbaijani", "Azərbaycan dili"),
                        new Language("be", "Belarusian", "Беларуская мова"),
                        new Language("bg", "Bulgarian", "Български"),
                        new Language("bn", "Bengali", "বাংলা"),
                        new Language("bs", "Bosnian", "Bosanski jezik"),
                        new Language("ca", "Catalan", "Català"),
                        new Language("co", "Corsican", "Corsu"),
                        new Language("cs", "Czech", "Čeština"),
                        new Language("cy", "Welsh", "Cymraeg"),
                        new Language("da", "Danish", "Dansk"),
                        new Language("de", "German", "Deutsch"),
                        new Language("el", "Greek", "Ελληνικά"),
                        new Language("en", "English", "English"),
                        new Language("eo", "Esperanto", "Esperanto"),
                        new Language("es", "Spanish", "Español"),
                        new Language("et", "Estonian", "Eesti"),
                        new Language("eu", "Basque", "Euskara"),
                        new Language("fa", "Persian", "فارسی"),
                        new Language("fi", "Finnish", "Suomi"),
                        new Language("fr", "French", "Français"),
                        new Language("ga", "Irish", "Gaeilge"),
                        new Language("gd", "Scots Gaelic", "Gàidhlig"),
                        new Language("gl", "Galician", "Galego"),
                        new Language("gu", "Gujarati", "ગુજરાતી"),
                        new Language("ha", "Hausa", "هَوُسَ"),
                        new Language("he", "Hebrew", "עִבְרִית"),
                        new Language("hi", "Hindi", "हिन्दी"),
                        new Language("hr", "Croatian", "Hrvatski"),
                        new Language("ht", "Haitian Creole", "Kreyòl ayisyen"),
                        new Language("hu", "Hungarian", "Magyar"),
                        new Language("hy", "Armenian", "Հայերեն"),
                        new Language("id", "Indonesian", "Bahasa Indonesia"),
                        new Language("ig", "Igbo", "Asụsụ Igbo"),
                        new Language("is", "Icelandic", "Íslenska"),
                        new Language("it", "Italian", "Italiano"),
                        new Language("ja", "Japanese", "日本語"),
                        new Language("jv", "Javanese", "Basa Jawa"),
                        new Language("ka", "Georgian", "ქართული"),
                        new Language("kk", "Kazakh", "Қазақ тілі"),
                        new Language("km", "Khmer", "ភាសាខ្មែរ"),
                        new Language("kn", "Kannada", "ಕನ್ನಡ"),
                        new Language("ko", "Korean", "한국어"),
                        new Language("ku", "Kurdish", "Kurdî"),
                        new Language("ky", "Kyrgyz", "Кыргызча"),
                        new Language("la", "Latin", "Latina"),
                        new Language("lb", "Luxembourgish", "Lëtzebuergesch"),
                        new Language("lo", "Lao", "ພາສາລາວ"),
                        new Language("lt", "Lithuanian", "Lietuvių kalba"),
                        new Language("lv", "Latvian", "Latviešu valoda"),
                        new Language("mg", "Malagasy", "Malagasy"),
                        new Language("mi", "Maori", "Te Reo Māori"),
                        new Language("mk", "Macedonian", "Македонски јазик"),
                        new Language("ml", "Malayalam", "മലയാളം"),
                        new Language("mn", "Mongolian", "Монгол хэл"),
                        new Language("mr", "Marathi", "मराठी"),
                        new Language("ms", "Malay", "Bahasa Melayu"),
                        new Language("mt", "Maltese", "Malti"),
                        new Language("my", "Myanmar", "ဗမာစာ"),
                        new Language("ne", "Nepali", "नेपाली"),
                        new Language("nl", "Dutch", "Nederlands"),
                        new Language("no", "Norwegian", "Norsk"),
                        new Language("ny", "Nyanja", "ChiCheŵa"),
                        new Language("or", "Odia", "ଓଡ଼ିଆ"),
                        new Language("pa", "Punjabi", "ਪੰਜਾਬੀ"),
                        new Language("pl", "Polish", "Polski"),
                        new Language("ps", "Pashto", "پښتو"),
                        new Language("pt", "Portuguese", "Português"),
                        new Language("ro", "Romanian", "Română"),
                        new Language("ru", "Russian", "Русский"),
                        new Language("rw", "Kinyarwanda", "Ikinyarwanda"),
                        new Language("sd", "Sindhi", "سنڌي"),
                        new Language("si", "Sinhala", "සිංහල"),
                        new Language("sk", "Slovak", "Slovenčina"),
                        new Language("sl", "Slovenian", "Slovenščina"),
                        new Language("sm", "Samoan", "Gagana Samoa"),
                        new Language("sn", "Shona", "ChiShona"),
                        new Language("so", "Somali", "Soomaali"),
                        new Language("sq", "Albanian", "Shqip"),
                        new Language("sr", "Serbian", "Српски језик"),
                        new Language("st", "Sesotho", "Sesotho"),
                        new Language("su", "Sundanese", "Basa Sunda"),
                        new Language("sv", "Swedish", "Svenska"),
                        new Language("sw", "Swahili", "Kiswahili"),
                        new Language("ta", "Tamil", "தமிழ்"),
                        new Language("te", "Telugu", "తెలుగు"),
                        new Language("tg", "Tajik", "Тоҷикӣ"),
                        new Language("th", "Thai", "ไทย"),
                        new Language("tk", "Turkmen", "Türkmençe"),
                        new Language("tl", "Tagalog", "Wikang Tagalog"),
                        new Language("tr", "Turkish", "Türkçe"),
                        new Language("tt", "Tatar", "Татарча"),
                        new Language("ug", "Uyghur", "ئۇيغۇرچە"),
                        new Language("uk", "Ukrainian", "Українська"),
                        new Language("ur", "Urdu", "اردو"),
                        new Language("uz", "Uzbek", "Oʻzbekcha"),
                        new Language("vi", "Vietnamese", "Tiếng Việt"),
                        new Language("xh", "Xhosa", "isiXhosa"),
                        new Language("yi", "Yiddish", "ייִדיש"),
                        new Language("yo", "Yoruba", "Yorùbá"),
                        new Language("zh", "Chinese", "中文"),
                        new Language("zu", "Zulu", "isiZulu")));
    }
}
