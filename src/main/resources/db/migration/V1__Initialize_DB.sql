CREATE TABLE languages
(
    code    VARCHAR(255) NOT NULL,
    name    VARCHAR(64)  NOT NULL,
    endonym VARCHAR(64)  NOT NULL,
    CONSTRAINT pk_languages PRIMARY KEY (code)
);

INSERT INTO languages (code, name, endonym)
VALUES ('af', 'Afrikaans', 'Afrikaans'),
       ('am', 'Amharic', 'አማርኛ'),
       ('ar', 'Arabic', 'العربية'),
       ('az', 'Azerbaijani', 'Azərbaycan dili'),
       ('be', 'Belarusian', 'Беларуская мова'),
       ('bg', 'Bulgarian', 'Български'),
       ('bn', 'Bengali', 'বাংলা'),
       ('bs', 'Bosnian', 'Bosanski jezik'),
       ('ca', 'Catalan', 'Català'),
       ('co', 'Corsican', 'Corsu'),
       ('cs', 'Czech', 'Čeština'),
       ('cy', 'Welsh', 'Cymraeg'),
       ('da', 'Danish', 'Dansk'),
       ('de', 'German', 'Deutsch'),
       ('el', 'Greek', 'Ελληνικά'),
       ('en', 'English', 'English'),
       ('eo', 'Esperanto', 'Esperanto'),
       ('es', 'Spanish', 'Español'),
       ('et', 'Estonian', 'Eesti'),
       ('eu', 'Basque', 'Euskara'),
       ('fa', 'Persian', 'فارسی'),
       ('fi', 'Finnish', 'Suomi'),
       ('fr', 'French', 'Français'),
       ('ga', 'Irish', 'Gaeilge'),
       ('gd', 'Scots Gaelic', 'Gàidhlig'),
       ('gl', 'Galician', 'Galego'),
       ('gu', 'Gujarati', 'ગુજરાતી'),
       ('ha', 'Hausa', 'هَوُسَ'),
       ('he', 'Hebrew', 'עִבְרִית'),
       ('hi', 'Hindi', 'हिन्दी'),
       ('hr', 'Croatian', 'Hrvatski'),
       ('ht', 'Haitian Creole', 'Kreyòl ayisyen'),
       ('hu', 'Hungarian', 'Magyar'),
       ('hy', 'Armenian', 'Հայերեն'),
       ('id', 'Indonesian', 'Bahasa Indonesia'),
       ('ig', 'Igbo', 'Asụsụ Igbo'),
       ('is', 'Icelandic', 'Íslenska'),
       ('it', 'Italian', 'Italiano'),
       ('ja', 'Japanese', '日本語'),
       ('jv', 'Javanese', 'Basa Jawa'),
       ('ka', 'Georgian', 'ქართული'),
       ('kk', 'Kazakh', 'Қазақ тілі'),
       ('km', 'Khmer', 'ភាសាខ្មែរ'),
       ('kn', 'Kannada', 'ಕನ್ನಡ'),
       ('ko', 'Korean', '한국어'),
       ('ku', 'Kurdish', 'Kurdî'),
       ('ky', 'Kyrgyz', 'Кыргызча'),
       ('la', 'Latin', 'Latina'),
       ('lb', 'Luxembourgish', 'Lëtzebuergesch'),
       ('lo', 'Lao', 'ພາສາລາວ'),
       ('lt', 'Lithuanian', 'Lietuvių kalba'),
       ('lv', 'Latvian', 'Latviešu valoda'),
       ('mg', 'Malagasy', 'Malagasy'),
       ('mi', 'Maori', 'Te Reo Māori'),
       ('mk', 'Macedonian', 'Македонски јазик'),
       ('ml', 'Malayalam', 'മലയാളം'),
       ('mn', 'Mongolian', 'Монгол хэл'),
       ('mr', 'Marathi', 'मराठी'),
       ('ms', 'Malay', 'Bahasa Melayu'),
       ('mt', 'Maltese', 'Malti'),
       ('my', 'Myanmar', 'ဗမာစာ'),
       ('ne', 'Nepali', 'नेपाली'),
       ('nl', 'Dutch', 'Nederlands'),
       ('no', 'Norwegian', 'Norsk'),
       ('ny', 'Nyanja', 'ChiCheŵa'),
       ('or', 'Odia', 'ଓଡ଼ିଆ'),
       ('pa', 'Punjabi', 'ਪੰਜਾਬੀ'),
       ('pl', 'Polish', 'Polski'),
       ('ps', 'Pashto', 'پښتو'),
       ('pt', 'Portuguese', 'Português'),
       ('ro', 'Romanian', 'Română'),
       ('ru', 'Russian', 'Русский'),
       ('rw', 'Kinyarwanda', 'Ikinyarwanda'),
       ('sd', 'Sindhi', 'سنڌي'),
       ('si', 'Sinhala', 'සිංහල'),
       ('sk', 'Slovak', 'Slovenčina'),
       ('sl', 'Slovenian', 'Slovenščina'),
       ('sm', 'Samoan', 'Gagana Samoa'),
       ('sn', 'Shona', 'ChiShona'),
       ('so', 'Somali', 'Soomaali'),
       ('sq', 'Albanian', 'Shqip'),
       ('sr', 'Serbian', 'Српски језик'),
       ('st', 'Sesotho', 'Sesotho'),
       ('su', 'Sundanese', 'Basa Sunda'),
       ('sv', 'Swedish', 'Svenska'),
       ('sw', 'Swahili', 'Kiswahili'),
       ('ta', 'Tamil', 'தமிழ்'),
       ('te', 'Telugu', 'తెలుగు'),
       ('tg', 'Tajik', 'Тоҷикӣ'),
       ('th', 'Thai', 'ไทย'),
       ('tk', 'Turkmen', 'Türkmençe'),
       ('tl', 'Tagalog', 'Wikang Tagalog'),
       ('tr', 'Turkish', 'Türkçe'),
       ('tt', 'Tatar', 'Татарча'),
       ('ug', 'Uyghur', 'ئۇيغۇرچە'),
       ('uk', 'Ukrainian', 'Українська'),
       ('ur', 'Urdu', 'اردو'),
       ('uz', 'Uzbek', 'Oʻzbekcha'),
       ('vi', 'Vietnamese', 'Tiếng Việt'),
       ('xh', 'Xhosa', 'isiXhosa'),
       ('yi', 'Yiddish', 'ייִדיש'),
       ('yo', 'Yoruba', 'Yorùbá'),
       ('zh', 'Chinese', '中文'),
       ('zu', 'Zulu', 'isiZulu');
