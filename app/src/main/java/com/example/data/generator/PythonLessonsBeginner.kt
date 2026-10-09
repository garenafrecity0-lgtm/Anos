package com.example.data.generator

import com.example.data.model.LessonSection
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.QuizQuestion

object PythonLessonsBeginner {
    val lessons: List<PythonLesson> = listOf(
        // =========================================================================
        // LEÇON 1 : LA FONCTION PRINT() ET LES COMMENTAIRES
        // STRICT : AUCUNE VARIABLE, AUCUN CALCUL, AUCUN IF, AUCUNE BOUCLE
        // =========================================================================
        PythonLesson(
            id = "py_01_intro",
            number = 1,
            title = "1. Votre Premier Ordre : print() & Commentaires",
            subtitle = "Découvrir la console, afficher du texte et des nombres, et écrire des notes avec #",
            level = PythonLevel.DEBUTANT,
            durationMinutes = 8,
            xpReward = 50,
            summary = "Apprenez comment un ordinateur lit votre code ligne par ligne et comment lui donner votre tout premier ordre d'affichage sans aucune erreur.",
            sections = listOf(
                LessonSection(
                    title = "1. Comment un ordinateur lit-il un programme ?",
                    content = """
Bienvenue dans votre apprentissage de Python !

Un programme informatique n'est rien d'autre qu'une suite d'instructions précises écrites pour l'ordinateur, exactement comme une recette de cuisine.

L'ordinateur est extrêmement rapide, mais il est totalement bête : il ne devine rien. Il lit votre fichier texte de haut en bas, ligne après ligne, dans l'ordre strict où vous l'avez écrit.

Pour communiquer avec nous, l'ordinateur utilise un écran de texte appelé la « Console » (ou le Terminal). Votre toute première mission de développeur est d'ordonner à l'ordinateur d'écrire un message sur cette console.
                    """.trimIndent(),
                    tip = "En Python, chaque nouvelle instruction s'écrit sur une nouvelle ligne. Pas besoin de point-virgule à la fin !"
                ),
                LessonSection(
                    title = "2. La fonction print() dans les moindres détails",
                    content = """
Pour afficher quelque chose à l'écran, Python nous fournit un ordre fondamental : le mot `print`.
En anglais, « print » signifie imprimer ou afficher.

Comment fonctionne cette instruction ?
1. On écrit le nom de l'ordre en minuscules : `print`.
2. On ouvre des parenthèses `(` et on les ferme `)`. Ces parenthèses sont comme des bras ouverts : tout ce que vous mettez à l'intérieur sera envoyé à l'écran.
3. Si vous voulez afficher du texte pour un humain, vous DEVEZ l'entourer de guillemets doubles `"` ou simples `'`. Les guillemets indiquent à Python : « Ceci est du texte brut, ne cherche pas à l'interpréter comme du code informatique ».
4. Si vous voulez afficher un nombre, PAS DE GUILLEMETS ! L'ordinateur comprend les chiffres directement.
                    """.trimIndent(),
                    codeSnippet = """
# Afficher du texte (avec des guillemets obligatoires)
print("Bonjour tout le monde !")
print('Bienvenue dans Anos py')

# Afficher des nombres (sans guillemets)
print(42)
print(2025)
                    """.trimIndent(),
                    expectedOutput = """
Bonjour tout le monde !
Bienvenue dans Anos py
42
2025
                    """.trimIndent(),
                    warning = "Python est sensible à la casse (majuscules/minuscules). Si vous écrivez Print avec un grand P, Python refusera d'exécuter et affichera une erreur : NameError: name 'Print' is not defined !"
                ),
                LessonSection(
                    title = "3. Afficher plusieurs choses à la suite avec la virgule",
                    content = """
Que faire si vous voulez afficher un mot et un nombre sur la même ligne ?
À l'intérieur des parenthèses de `print()`, vous pouvez séparer vos éléments par une simple virgule `,`.
Python va automatiquement afficher chaque élément et ajouter un espace propre entre eux !
                    """.trimIndent(),
                    codeSnippet = """
print("Mon score est de", 100, "points.")
print("Étape", 1, ":", "Départ réussi !")
                    """.trimIndent(),
                    expectedOutput = """
Mon score est de 100 points.
Étape 1 : Départ réussi !
                    """.trimIndent()
                ),
                LessonSection(
                    title = "4. Les Commentaires avec le dièse (#)",
                    content = """
Dans votre code, vous aurez souvent besoin d'écrire des explications pour vous souvenir de ce que vous faites, ou pour aider d'autres humains qui lisent votre travail.

Ces explications s'appellent des « Commentaires ».
En Python, tout ce qui se trouve après le caractère dièse `#` est totalement invisible pour l'ordinateur. Il l'ignore complètement et ne l'exécute pas.
                    """.trimIndent(),
                    codeSnippet = """
# Ceci est un commentaire : Python ne fera absolument rien avec cette ligne
print("Seule cette ligne sera affichée à l'écran") # On peut aussi commenter ici
                    """.trimIndent(),
                    expectedOutput = """
Seule cette ligne sera affichée à l'écran
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_01_1",
                    question = "Pourquoi met-on des guillemets autour du texte dans print(\"Bonjour\") ?",
                    codeSnippet = null,
                    options = listOf(
                        "Pour que le texte s'affiche en gras",
                        "Pour indiquer à Python qu'il s'agit de texte humain et non d'une commande",
                        "Les guillemets sont facultatifs en Python",
                        "Pour crypter le message"
                    ),
                    correctIndex = 1,
                    explanation = "Les guillemets délimitent le texte humain. Sans guillemets, Python essaie de trouver un ordre ou une instruction portant ce nom et provoque une erreur."
                ),
                QuizQuestion(
                    id = "q_01_2",
                    question = "Que va faire l'ordinateur en lisant la ligne suivante : # print(\"Coucou\") ?",
                    codeSnippet = "# print(\"Coucou\")",
                    options = listOf(
                        "Il va afficher 'Coucou'",
                        "Il va lever une erreur de syntaxe",
                        "Il va ignorer totalement la ligne car elle commence par un #",
                        "Il va afficher '# Coucou'"
                    ),
                    correctIndex = 2,
                    explanation = "Le symbole # transforme toute la ligne en commentaire. L'ordinateur l'ignore à 100%, rien ne sera affiché."
                ),
                QuizQuestion(
                    id = "q_01_3",
                    question = "Que va afficher l'instruction : print(\"Niveau\", 1) ?",
                    codeSnippet = "print(\"Niveau\", 1)",
                    options = listOf(
                        "Niveau1 (collé)",
                        "Niveau 1 (avec un espace automatique)",
                        "Une erreur car on mélange texte et nombre",
                        "\"Niveau\" 1"
                    ),
                    correctIndex = 1,
                    explanation = "En séparant les éléments par une virgule dans print(), Python les affiche tous sur la même ligne en insérant un espace entre eux."
                )
            )
        ),

        // =========================================================================
        // LEÇON 2 : LES VARIABLES & LES 4 TYPES PRIMITIFS
        // STRICT : UNIQUEMENT DES VARIABLES, LEURS 4 TYPES ET PRINT()
        // AUCUN CALCUL (C'EST LA LEÇON 3), AUCUN IF, AUCUN INPUT
        // =========================================================================
        PythonLesson(
            id = "py_02_variables",
            number = 2,
            title = "2. Les Variables & Les 4 Types Fondamentaux",
            subtitle = "L'analogie de la boîte étiquetée, le signe égal, int, float, str et bool",
            level = PythonLevel.DEBUTANT,
            durationMinutes = 9,
            xpReward = 60,
            summary = "Découvrez comment l'ordinateur garde des informations dans sa mémoire vive grâce aux variables et comprenez la différence entre du texte, des entiers, des décimaux et des booléens.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce qu'une variable ? (L'analogie de la boîte)",
                    content = """
Dans la leçon 1, nous affichions des messages fixes. Mais un programme a besoin de se souvenir d'informations qui changent : le prénom d'un joueur, son score, son niveau...

Pour retenir une information, l'ordinateur utilise sa mémoire vive (la RAM).
Une **Variable**, c'est exactement comme une boîte de rangement avec une étiquette dessus :
• L'étiquette, c'est le **nom** de la variable (par exemple : `pseudo`).
• Ce qui est rangé à l'intérieur de la boîte, c'est la **valeur** (par exemple : `"Anos"`).

Pour ranger une valeur dans une boîte, on utilise le symbole égal `=` :
`nom_de_la_boite = valeur`
                    """.trimIndent(),
                    codeSnippet = """
# On range "Anos" dans la variable pseudo
pseudo = "Anos"

# On range le nombre 10 dans la variable niveau
niveau = 10

# Pour afficher le contenu d'une boîte, on donne son nom SANS GUILLEMETS à print() !
print(pseudo)
print(niveau)
print("Joueur actuel :", pseudo)
                    """.trimIndent(),
                    expectedOutput = """
Anos
10
Joueur actuel : Anos
                    """.trimIndent(),
                    warning = "Attention au piège suprême : print(pseudo) affiche le contenu de la variable ('Anos'). Mais print(\"pseudo\") avec des guillemets affiche littéralement les 6 lettres p-s-e-u-d-o !"
                ),
                LessonSection(
                    title = "2. Les 4 Types de données fondamentaux",
                    content = """
En Python, toutes les données ne se ressemblent pas. Il existe 4 types indispensables que tout développeur doit connaître par cœur :

1. `str` (String / Chaîne de caractères) : Du texte humain entouré de guillemets.
   Exemple : `"Bonjour"`, `'Paris'`, `"123"` (ici 123 est du texte car il a des guillemets !).

2. `int` (Integer / Nombre Entier) : Un nombre sans virgule (positif, négatif ou zéro).
   Exemple : `15`, `0`, `-8`.

3. `float` (Flottant / Nombre Décimal) : Un nombre à virgule.
   ATTENTION : En programmation, on utilise TOUJOURS un point `.` et jamais une virgule !
   Exemple : `19.99`, `3.14`, `0.5`.

4. `bool` (Booléen) : Un état de vérité qui ne peut valoir que DEUX choses au monde :
   `True` (Vrai) ou `False` (Faux). Toujours avec une majuscule !
                    """.trimIndent(),
                    codeSnippet = """
titre = "Anos py"     # Type str
score = 450           # Type int
moyenne = 17.5        # Type float
partie_terminee = False # Type bool

# On peut demander à Python le type d'une boîte avec type()
print(type(titre))
print(type(score))
print(type(moyenne))
print(type(partie_terminee))
                    """.trimIndent(),
                    expectedOutput = """
<class 'str'>
<class 'int'>
<class 'float'>
<class 'bool'>
                    """.trimIndent(),
                    tip = "Les noms de variables s'écrivent en minuscules avec des tirets bas pour séparer les mots (style snake_case) : score_du_joueur, points_de_vie."
                ),
                LessonSection(
                    title = "3. Changer la valeur d'une boîte (Réaffectation)",
                    content = """
Une variable porte bien son nom : sa valeur peut varier au cours du programme !
Si vous remettez une nouvelle valeur dans la même boîte, l'ancienne valeur est effacée et remplacée.
                    """.trimIndent(),
                    codeSnippet = """
points = 10
print("Au départ :", points)

# On remplace le contenu de la boîte
points = 50
print("Après modification :", points)
                    """.trimIndent(),
                    expectedOutput = """
Au départ : 10
Après modification : 50
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_02_1",
                    question = "Quelle est la différence entre print(nom) et print(\"nom\") ?",
                    codeSnippet = "nom = \"Sarah\"",
                    options = listOf(
                        "Aucune différence, les deux affichent 'Sarah'",
                        "print(nom) affiche la valeur 'Sarah', alors que print(\"nom\") affiche le mot 'nom'",
                        "print(nom) provoque une erreur",
                        "print(\"nom\") affiche une boîte vide"
                    ),
                    correctIndex = 1,
                    explanation = "Sans guillemets, Python cherche la variable appelée 'nom' et lit son contenu ('Sarah'). Avec des guillemets, Python affiche simplement le texte brut 'nom'."
                ),
                QuizQuestion(
                    id = "q_02_2",
                    question = "Quel est le type exact de la variable x = \"42\" (avec les guillemets) ?",
                    codeSnippet = "x = \"42\"",
                    options = listOf("int (entier)", "float (décimal)", "str (chaîne de caractères)", "bool"),
                    correctIndex = 2,
                    explanation = "Dès qu'une valeur est entourée de guillemets, même s'il s'agit de chiffres, Python la considère comme du texte (type str)."
                ),
                QuizQuestion(
                    id = "q_02_3",
                    question = "Quelle valeur booléenne s'écrit correctement en Python ?",
                    codeSnippet = null,
                    options = listOf("true", "TRUE", "True (avec T majuscule)", "\"True\""),
                    correctIndex = 2,
                    explanation = "En Python, les deux seuls booléens s'écrivent obligatoirement avec une majuscule : True et False."
                )
            )
        ),

        // =========================================================================
        // LEÇON 3 : LES OPÉRATEURS ARITHMÉTIQUES ET CALCULS
        // STRICT : CALCULS AVEC DES VARIABLES ET PRINT()
        // AUCUN IF, AUCUNE BOUCLE, AUCUN INPUT
        // =========================================================================
        PythonLesson(
            id = "py_03_operators",
            number = 3,
            title = "3. Les Opérations Mathématiques en Python",
            subtitle = "+, -, *, /, //, %, ** et la priorité des calculs",
            level = PythonLevel.DEBUTANT,
            durationMinutes = 9,
            xpReward = 60,
            summary = "Transformez Python en une calculatrice ultra-puissante et comprenez la différence essentielle entre la division réelle, la division entière et le modulo.",
            sections = listOf(
                LessonSection(
                    title = "1. Les 4 Opérations de Base (+, -, *, /)",
                    content = """
Maintenant que vous savez ranger des nombres dans des variables, apprenons à calculer avec !

Python connaît les opérations de base de l'école :
• `+` : Addition
• `-` : Soustraction
• `*` : Multiplication (on utilise l'étoile/astérisque, pas la lettre 'x')
• `/` : Division classique (attention : la division avec un seul slash donne TOUJOURS un résultat décimal `float`, même si le résultat tombe juste ! Exemple : `10 / 2` donne `5.0`).
                    """.trimIndent(),
                    codeSnippet = """
prix_unitaire = 12
quantite = 3

total = prix_unitaire * quantite
print("Prix total :", total)

remise = 6
prix_final = total - remise
print("Après remise :", prix_final)

partage = 20 / 4
print("20 divisé par 4 donne :", partage)
                    """.trimIndent(),
                    expectedOutput = """
Prix total : 36
Après remise : 30
20 divisé par 4 donne : 5.0
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Les 3 Opérateurs Spéciaux (//, %, **)",
                    content = """
Python propose 3 opérateurs magiques que tout programmeur utilise quotidiennement :

1. La Division Entière `//` (double slash) :
   Elle calcule la division et retire complètement la partie décimale sans arrondir !
   Exemple : `7 // 2` donne `3` (car 2 rentre 3 fois dans 7).

2. Le Modulo `%` (symbole pourcentage) :
   Il donne le **RESTE** de la division entière !
   Exemple : dans `7 // 2`, on met 3 fois 2 (ce qui fait 6), et il reste `1`.
   Donc `7 % 2` vaut `1`.
   Astuce : si `nombre % 2` vaut `0`, le nombre est pair ! S'il vaut `1`, il est impair.

3. La Puissance `**` (double étoile) :
   Calcule a élevé à la puissance b.
   Exemple : `2 ** 3` = 2 x 2 x 2 = 8.
                    """.trimIndent(),
                    codeSnippet = """
bonbons = 14
enfants = 4

par_enfant = bonbons // enfants
reste = bonbons % enfants

print("Chaque enfant reçoit :", par_enfant, "bonbons")
print("Il reste dans le paquet :", reste, "bonbons")

print("2 à la puissance 4 :", 2 ** 4)
                    """.trimIndent(),
                    expectedOutput = """
Chaque enfant reçoit : 3 bonbons
Il reste dans le paquet : 2 bonbons
2 à la puissance 4 : 16
                    """.trimIndent()
                ),
                LessonSection(
                    title = "3. L'addition de texte (Concaténation)",
                    content = """
Que se passe-t-il si vous utilisez le signe `+` entre deux chaînes de texte ?
Python ne fait pas de calcul mathématique : il colle les deux textes l'un après l'autre ! C'est ce qu'on appelle la **concaténation**.
                    """.trimIndent(),
                    codeSnippet = """
mot1 = "Super"
mot2 = "Héros"
combinaison = mot1 + " " + mot2
print(combinaison)
                    """.trimIndent(),
                    expectedOutput = """
Super Héros
                    """.trimIndent(),
                    warning = "On ne peut PAS additionner du texte et un nombre : 'Age : ' + 25 fera planter Python avec un TypeError ! Il faut convertir le nombre en texte avec str(25)."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_03_1",
                    question = "Quel est le résultat de l'instruction print(10 // 3) ?",
                    codeSnippet = "print(10 // 3)",
                    options = listOf("3.33", "3", "1", "4"),
                    correctIndex = 1,
                    explanation = "// est la division entière. 3 rentre 3 fois entières dans 10, le résultat est donc 3."
                ),
                QuizQuestion(
                    id = "q_03_2",
                    question = "Quel est le résultat de l'instruction print(10 % 3) ?",
                    codeSnippet = "print(10 % 3)",
                    options = listOf("3", "1", "0", "0.33"),
                    correctIndex = 1,
                    explanation = "% est le modulo (le reste). 10 divisé par 3 fait 3 fois 3 (9), et il reste 1."
                ),
                QuizQuestion(
                    id = "q_03_3",
                    question = "Que donne l'instruction : print(2 ** 3) ?",
                    codeSnippet = "print(2 ** 3)",
                    options = listOf("6", "8", "5", "9"),
                    correctIndex = 1,
                    explanation = "** représente la puissance : 2 puissance 3 = 2 * 2 * 2 = 8."
                )
            )
        ),

        // =========================================================================
        // LEÇON 4 : ENTRÉES UTILISATEUR & FORMATAGE F-STRINGS
        // STRICT : INPUT(), CONVERSIONS STR/INT ET F-STRINGS
        // AUCUN IF, AUCUNE BOUCLE
        // =========================================================================
        PythonLesson(
            id = "py_04_strings_io",
            number = 4,
            title = "4. Dialoguer avec l'Humain : input() & f-strings",
            subtitle = "Écouter l'utilisateur, convertir la saisie et formater du texte moderne",
            level = PythonLevel.DEBUTANT,
            durationMinutes = 9,
            xpReward = 60,
            summary = "Apprenez à poser des questions à l'utilisateur, à récupérer ce qu'il tape au clavier, et à injecter vos variables directement dans votre texte avec les puissantes f-strings.",
            sections = listOf(
                LessonSection(
                    title = "1. La fonction input() pour écouter l'utilisateur",
                    content = """
Jusqu'à maintenant, toutes nos variables avaient des valeurs écrites à l'avance.
Pour rendre un programme interactif, l'ordinateur doit pouvoir poser une question et attendre la réponse de l'humain !

C'est exactement le rôle de la fonction `input("Votre question : ")` :
1. L'ordinateur affiche la question dans la console.
2. Il **se met en pause** et attend que l'utilisateur tape du texte au clavier puis appuie sur la touche **Entrée**.
3. Tout ce que l'utilisateur a tapé est alors renvoyé pour être rangé dans une variable !
                    """.trimIndent(),
                    codeSnippet = """
# Exemple de dialogue :
# prenom = input("Comment t'appelles-tu ? ")
# print("Enchanté", prenom)

# Simulation de saisie
prenom = "Thomas"
print("Bonjour", prenom, "!")
                    """.trimIndent(),
                    expectedOutput = """
Bonjour Thomas !
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La Règle d'Or d'input() : Toujours du Texte !",
                    content = """
Voici le piège dans lequel tombent 100% des débutants :
La fonction `input()` renvoie **TOUJOURS une chaîne de caractères (str)**, même si l'utilisateur a tapé des chiffres !

Si l'utilisateur tape `20`, vous recevez le texte `"20"`.
Si vous faites ensuite `"20" + 5`, Python plantera !
Pour faire des calculs avec ce que l'utilisateur a tapé, vous devez OBLIGATOIREMENT convertir le texte en nombre avec `int()` :
                    """.trimIndent(),
                    codeSnippet = """
# On simule ce que input() renvoie
age_texte = "18"

# On convertit le texte "18" en vrai nombre 18
age_nombre = int(age_texte)

# Maintenant on peut calculer sans erreur !
dans_cinq_ans = age_nombre + 5
print("Dans 5 ans, vous aurez :", dans_cinq_ans, "ans")
                    """.trimIndent(),
                    expectedOutput = """
Dans 5 ans, vous aurez : 23 ans
                    """.trimIndent(),
                    tip = "En pratique, les développeurs écrivent directement : age = int(input('Quel est votre âge ? ')) pour convertir dès la saisie !"
                ),
                LessonSection(
                    title = "3. La Magie des f-strings (Formatage Moderne)",
                    content = """
Plutôt que d'écrire des virgules partout comme `print("Bonjour", prenom, "tu as", age, "ans")`, Python 3 a inventé la méthode la plus élégante du monde : les **f-strings** !

Comment ça marche ?
1. Vous placez la lettre `f` minuscule juste devant vos guillemets : `f"..."`.
2. À l'intérieur du texte, vous insérez directement vos variables entre des accolades `{nom_variable}`.
Python va automatiquement remplacer les accolades par la vraie valeur !
                    """.trimIndent(),
                    codeSnippet = """
joueur = "Léa"
score = 85
niveau = 3

# Une seule phrase limpide et facile à lire :
message = f"Bravo {joueur} ! Tu as atteint le niveau {niveau} avec {score} points."
print(message)

# On peut même glisser un calcul directement dans l'accolade !
print(f"Le double de ton score est : {score * 2}")
                    """.trimIndent(),
                    expectedOutput = """
Bravo Léa ! Tu as atteint le niveau 3 avec 85 points.
Le double de ton score est : 170
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_04_1",
                    question = "Quel est le type de donnée retourné par la fonction input() ?",
                    codeSnippet = null,
                    options = listOf(
                        "Toujours du texte (type str)",
                        "Un entier si l'utilisateur tape un nombre",
                        "Un booléen",
                        "Rien du tout"
                    ),
                    correctIndex = 0,
                    explanation = "input() lit les frappes du clavier sous forme de texte brut. La valeur renvoyée est systématiquement de type str."
                ),
                QuizQuestion(
                    id = "q_04_2",
                    question = "Quel code affiche correctement 'Prix : 30 €' avec une f-string ?",
                    codeSnippet = "prix = 30",
                    options = listOf(
                        "print('Prix : {prix} €')",
                        "print(f'Prix : {prix} €')",
                        "print(f'Prix : prix €')",
                        "print('Prix : ' + prix + ' €')"
                    ),
                    correctIndex = 1,
                    explanation = "Il faut impérativement mettre le 'f' devant les guillemets et entourer la variable d'accolades : f'Prix : {prix} €'."
                )
            )
        ),

        // =========================================================================
        // LEÇON 5 : LES CONDITIONS ET BRANCHEMENTS (IF, ELIF, ELSE)
        // STRICT : PRÉREQUIS VARIABLES, CALCULS, INPUT, F-STRINGS
        // AUCUNE BOUCLE (C'EST LA LEÇON 6), AUCUNE LISTE (LEÇON 7), AUCUNE FONCTION DEF
        // =========================================================================
        PythonLesson(
            id = "py_05_conditionals",
            number = 5,
            title = "5. Prendre des Décisions : if, elif, else",
            subtitle = "Les comparaisons, l'indentation obligatoire et les embranchements",
            level = PythonLevel.DEBUTANT,
            durationMinutes = 10,
            xpReward = 70,
            summary = "Donnez de l'intelligence à vos programmes en apprenant à exécuter des blocs de code seulement si certaines conditions sont respectées.",
            sections = listOf(
                LessonSection(
                    title = "1. Les Opérateurs de Comparaison (==, !=, <, >)",
                    content = """
Jusqu'ici, notre code s'exécutait en ligne droite du début à la fin.
Mais dans la vraie vie, un programme doit faire des choix :
• SI le mot de passe est bon -> ouvrir la session.
• SINON -> afficher une erreur.

Pour comparer deux valeurs, on utilise des opérateurs qui renvoient `True` ou `False` :
• `==` (double égal) : Est égal à ? (ATTENTION : un seul `=` sert à ranger dans une variable, le double `==` sert à comparer !).
• `!=` : Est différent de ?
• `>` et `<` : Plus grand que / Plus petit que.
• `>=` et `<=` : Supérieur ou égal / Inférieur ou égal.
                    """.trimIndent(),
                    codeSnippet = """
age = 18

print("Est majeur :", age >= 18)      # Affiche True
print("A exactement 10 ans :", age == 10) # Affiche False
print("N'a pas 20 ans :", age != 20)  # Affiche True
                    """.trimIndent(),
                    expectedOutput = """
Est majeur : True
A exactement 10 ans : False
N'a pas 20 ans : True
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La structure if (Si) et l'Indentation",
                    content = """
Le mot-clé `if` signifie « SI » en anglais.
On écrit :
`if condition:`
Puis, sur la ligne suivante, on **DÉCALE le code vers la droite de 4 espaces** !
Ce décalage s'appelle l'**Indentation**.
C'est la règle d'or absolue de Python : tout ce qui est décalé vers la droite n'est exécuté QUE si la condition est vraie !
                    """.trimIndent(),
                    codeSnippet = """
score = 85

if score >= 50:
    print("Bravo, test réussi !") # Décalé de 4 espaces
    print("Vous passez à l'étape suivante.")

print("Cette ligne s'exécute toujours car elle n'est pas indentée.")
                    """.trimIndent(),
                    expectedOutput = """
Bravo, test réussi !
Vous passez à l'étape suivante.
Cette ligne s'exécute toujours car elle n'est pas indentée.
                    """.trimIndent(),
                    warning = "N'oubliez JAMAIS le deux-points ':' à la fin de la ligne du if ! C'est lui qui ouvre la porte au bloc de code indenté."
                ),
                LessonSection(
                    title = "3. Le plan B avec else et les choix multiples avec elif",
                    content = """
• `else:` (SINON) : s'exécute quand la condition du `if` était fausse.
• `elif:` (contraction de 'else if', SINON SI) : permet de tester une autre condition si la première n'a pas fonctionné.
                    """.trimIndent(),
                    codeSnippet = """
note = 14

if note >= 16:
    print("Mention Très Bien ! 🌟")
elif note >= 12:
    print("Mention Assez Bien ! 👍")
elif note >= 10:
    print("Admis de justesse ! ✅")
else:
    print("Rattrapage nécessaire... 📚")
                    """.trimIndent(),
                    expectedOutput = """
Mention Assez Bien ! 👍
                    """.trimIndent()
                ),
                LessonSection(
                    title = "4. Combiner avec and, or et not",
                    content = """
Vous pouvez lier plusieurs conditions avec les mots anglais :
• `and` (ET) : les deux conditions doivent être vraies en même temps.
• `or` (OU) : au moins une condition doit être vraie.
• `not` (NON) : inverse le résultat.
                    """.trimIndent(),
                    codeSnippet = """
age = 20
a_billet = True

if age >= 18 and a_billet:
    print("Accès autorisé au concert !")
                    """.trimIndent(),
                    expectedOutput = """
Accès autorisé au concert !
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_05_1",
                    question = "Quel symbole utilise-t-on pour tester si deux valeurs sont égales dans un if ?",
                    codeSnippet = null,
                    options = listOf("=", "==", "===", "equals"),
                    correctIndex = 1,
                    explanation = "Un seul égal '=' est une affectation (ranger une valeur). Pour tester l'égalité, on utilise impérativement le double égal '=='."
                ),
                QuizQuestion(
                    id = "q_05_2",
                    question = "Qu'est-ce que l'indentation en Python ?",
                    codeSnippet = null,
                    options = listOf(
                        "Une couleur de texte dans l'éditeur",
                        "Le décalage vers la droite (généralement 4 espaces) qui délimite un bloc d'instructions",
                        "Une fonction mathématique",
                        "Un type d'erreur"
                    ),
                    correctIndex = 1,
                    explanation = "L'indentation remplace les accolades d'autres langages. Elle indique visuellement et logiquement à Python quelles lignes appartiennent au bloc conditionnel."
                ),
                QuizQuestion(
                    id = "q_05_3",
                    question = "Que va afficher ce code si x = 7 ?",
                    codeSnippet = """
x = 7
if x > 10:
    print("Grand")
else:
    print("Petit")
                    """.trimIndent(),
                    options = listOf("Grand", "Petit", "Grand et Petit", "Rien"),
                    correctIndex = 1,
                    explanation = "La condition 'x > 10' est fausse car 7 n'est pas supérieur à 10. Le programme bascule donc dans la branche 'else' et affiche 'Petit'."
                )
            )
        )
    )
}
