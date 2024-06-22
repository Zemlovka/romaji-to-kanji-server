# 4IT353 - Druhá semestrální úloha
# Romaji to Kanji: Nástroj pro procvičování hiragany a katakany

Zpracováno: Mykhailo Bubnov, Nikita Korotov

### Úvod
Osvojení japonských psacích systémů, hiragany a katakana, představuje pro studenty tohoto
jazyka značnou výzvu. Navzdory dostupnosti mnoha vzdělávacích nástrojů mnozí studenti,
včetně mě, považují stávající zdroje, jako je Duolingo, za nedostatečné pro zvládnutí znaků
kana. V reakci na tuto mezeru vyvíjíme aplikaci s názvem "Romaji to Kanji," která je navržena k
usnadnění procvičování psaní japonských slov v káně, ať už z romaji (r2k) nebo naopak.
### Cíl
Hlavním cílem tohoto projektu je vytvořit poutavý a efektivní nástroj, který pomáhá studentům při
procvičování a upevňování znalostí hiragany a katakany. Tato aplikace si klade za cíl poskytnout
interaktivnější, uživatelsky přívětivější a krásnější zážitek ve srovnání s tradičními platformami
pro výuku jazyků.
### Popis
"Romaji to Kanji" je webová aplikace, která uživatele vyzývá k převodu daných slov mezi romaji
a kana. Aplikace nabízí dvě hlavní funkce:
- Romaji to Kana (r2k): Uživatelé dostanou slovo v romaji a musí ho napsat buď v
hiraganě, nebo katakaně.
- Kana to Romaji (k2r): Uživatelé dostanou slovo v hiraganě nebo katakaně a musí ho
přepsat do romaji.
- Sentence translate: Uživatel dostane větu a musí jí překlast/vybrat překlad z několika.
Funkce
Uživatelsky přívětivé rozhraní: Aplikace je navržena s jednoduchým a intuitivním rozhraním, což
ji činí přístupnou pro studenty všech úrovní.
- Okamžitá zpětná vazba: Uživatelé dostávají okamžitou zpětnou vazbu na své vstupy,
což umožňuje rychlou korekci a učení.
- Přizpůsobení: Aplikaci lze přizpůsobit na zaměření na konkrétní sady znaků nebo slov,
podle potřeb a úrovně pokročilosti studenta.
- Sledování pokroku: Uživatelé mohou sledovat svůj pokrok v čase a identifikovat oblasti,
které vyžadují více procvičování.
- Achievements: V aplikace je zaveden systém dosázení pro motivace uživatelů.
- Uživatelé mohou navrhovat vlastní slova a hlásit chyby ve slovech.
- Uživatelé mohou sledovat jiné uživatele.

### Neimplimitovane funkce
- Prace s kanji
- Prace se sentence
- Prace s achievements

### Pouzivaná db
Souborová h2 embedded ve springu

### Přiručká na spuštění
Až se spustí archiv romaji2kanji.jar pomocí přikazu

java -jar romaji2kanji.jar --spring.config.location=file:C:\Users\Bruh\Documents\vse\4IT353\romaji2kanji\src\main\resources\application.properties

Tak se spustí server

Customizovát ho chování je možní pomocí editace souboru application.properties, který lze připojít takto:
java -jar romaji2kanji.jar --spring.config.location=file:C:\Users\Bruh\Documents\vse\4IT353\romaji2kanji\src\main\resources\application.properties

A je možně provest změnu portu aplikace, databazí, použivaného databazového souboru, web path, 
zapnout/vypnout bezpečnost atd.

Pomocí souboru ve složce bruno je možně testovát tohle rozhrání (je potřeba si stahnout prográm "Bruno")

Po prvním spuštění splikace sama vygeneruje základní slovník a dva uživatele: 

- user:password(role: user)
- admin:password(role: admin) 

Hesla a role je možný měnít pomocí klienta a webového rozhrání, a taky i v application.properties