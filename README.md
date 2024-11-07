# Player
מטודות שיושמו: 9/9
## משתנים וקבועים
### isPlayerOne
#### ייעוד
בודק אם זה התור של השחקן
#### סוג
boolean
#### פרטיות ומצב
protected
### wins
#### ייעוד
כמות הנצחונות
#### סוג
int
#### פרטיות ומצב
protected
### initial_number_of_bombs
#### ייעוד
כמות הפצצות הראשוני. מוגדר כ3.
#### סוג
int
#### פרטיות ומצב
protected static final
### initial_number_of_unflippedable
#### ייעוד
כמות החיילים הבלתי-ניתנים לשינוי הראשוני. מוגרד כ2.
#### סוג
int
#### פרטיות ומצב
protected static final
### number_of_bombs
#### ייעוד
כמות הפצצות.
#### סוג
int
#### פרטיות ומצב
protected
### number_of_unflippedable
#### ייעוד
כמות החיילים הבלתי-ניתנים לשינוי.
#### סוג
int
#### פרטיות ומצב
protected
## קונסטרקטור
מקבל משתנה isPlayerOne. מריץ `reset_bombs_and_unflippedable` ומגדיר את isPlayerOne וwins(=0).
## מטודות
### isPlayerOne
מחזירה isPlayerOne.
### getWins
מחזירה wins.
### addWin
מוסיף נקודה לwins.
### isHuman
פונקציה אבסטרקטית.
### getNumber_of_bombs
מחזירה number_of_bombs.
### getNumber_of_unflippedable
מחזירה number_of_unflippedable.
### reduce_bomb
מוריד פצצה אחת.
### reduce_unflippedable
מוריד חייל בלתי-ניתן לשינוי אחד.
### reset_bombs_and_unflippedable
מגדיר את number_of_bombs ו-number_of_unflippedable כמצבם הראשוני (initial_number_of_unflippedable, initial_number_of_bombs).
# Disc
סוגים של הinterface שצריך ליישם: simpleDisc, unflipDisc, bombDisc.
## מטודות
### getOwner
מחזיר את owner.
### setOwner
מקבל אובייקט Player, ומגדיר את owner כערך הקלט.
### getType
מחזיר String המייצג את סוג החייל.
# Move
## משתנים וקבועים
### position
#### ייעוד
המיקום שבו שם השחקן חייל.
#### סוג
Position
#### פרטיות ומצב
protected static final
### disc
#### ייעוד
סוג החייל.
#### סוג
Disc
#### פרטיות ומצב
protected static final
# Position
מטודות שיושמו: 2/2
## משתנים וקבועים
### row
#### ייעוד
ערך הx בלוח.
#### סוג
double
#### פרטיות ומצב
protected
### col
#### ייעוד
ערך הy בלוח.
#### סוג
double
#### פרטיות ומצב
protected
## קונסטרקטור
מקבל 2 משתנים מסוג double, הקרויים col ו-row. מגדיר את col ו-row כערכי הקלט.
## מטודות
### row
מחזיר את row.
### col
מחזיר את col.
# AIPlayer
מטודות שיושמו: 6/6
## משתנים וקבועים
### aiPlayerRegistry
#### ייעוד
רשימת hash של כל שחקני AI במשחק, וסוגם.
#### סוג
Map
#### פרטיות ומצב
private static final

## קונסטרקטור
מחלקה יורשת מPlayer. הקונסטרקטור מוביל לקונסטרקטור של Player.
## מטודות
### registerAllAIPlayers
מפעיל registerAIPlayerType לכל סוג AIPlayer, ככתוב בPDF.
### isHuman
override: מחזיר false.
### registerAIPlayerType
מקבל שם String ואובייקט היורש מAIPlayer, ורושם אותו בaiPlayerRegistry.
### createAIPlayer
מקבל String עם סוג השחקן AI ו-bool המציין אם השחקן מוגדר כ"ראשון". המטודה מנסה להכין AIPlayer חדש, ומחזירה את האובייקט אם היא מצליחה.
### getAIPlayerTypes
מחזיר ArrayList של aiPlayerRegistry.
### makeMove
מקבל אובייקט PlayableLogic ומחזיר אובייקט Move.
# PlayableLogic
סוגים של הinterface שצריך ליישם: GameLogic
## מטודות
### locate_disc
מקבל Position ו-Disc ומחשב אם המיקום של הדיסק חוקי- מחזיר true אם כן, false אם לא.
### getDiscAtPosition
מקבל Position ומחזיר Disc שנמצא בPosition.
### getBoardSize
מחזיר int של "גודל הלוח", כתוב שם או מספר שורות או מספר עמודות, אז פשוט נבחר ונלך עם זה.
### ValidMoves
מחזיר רשימה של Position לפי המהלכים שהשחקן הנוכחי יכול לעשות.
### countFlips
מקבל Position ומחזיר int של כמות החיילים שיתהפכו.
### getFirstPlayer
מחזיר Player של השחקן שרשום כ"הראשון".
### getSecondPlayer
מחזיר Player של השחקן שרשום כ"השני".
### setPlayers
מקבל שתי אובייקטים מסוג Player ומגדיר את המסומן כראשון כ"player1" וכו לשני.
### isFirstPlayerTurn
אם זה התור של השחקן הראשון, מחזיר true. אם זה התור של השחקן השני, מחזיר false.
### isGameFinished
אם המשחק הסתיים, מחזיר true, אחרת מחזיר false.
### reset
מאפס את המשחק.
### undoLastMove
מחזירה אחורה את המשחק בתור אחד.
