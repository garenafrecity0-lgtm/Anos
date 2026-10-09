package com.example.data.generator

import com.example.data.model.LessonSection
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.QuizQuestion

object PythonLessonsIntermediate1 {
    val lessons: List<PythonLesson> = listOf(
        // =========================================================================
        // LEÇON 6 : LES BOUCLES (WHILE ET FOR AVEC RANGE)
        // STRICT : PRÉREQUIS VARIABLES, CONDITIONS (CH 5), OPÉRATEURS
        // AUCUNE LISTE (CH 7), AUCUN DICTIONNAIRE (CH 8), AUCUNE FONCTION DEF (CH 10)
        // =========================================================================
        PythonLesson(
            id = "py_06_loops",
            number = 6,
            title = "6. Répéter des Actions : Boucles while & for",
            subtitle = "Automatiser les tâches répétitives, range(), break et continue",
            level = PythonLevel.INTERMEDIAIRE_1,
            durationMinutes = 10,
            xpReward = 80,
            summary = "Découvrez comment répéter du code sans jamais le dupliquer grâce aux boucles 'while' et 'for', et apprenez à contrôler le flux avec break et continue.",
            sections = listOf(
                LessonSection(
                    title = "1. La boucle while (Tant que...)",
                    content = """
Imaginez devoir afficher « Bienvenue » 100 fois. Allez-vous copier-coller 100 fois la même ligne ? Absolument pas !
Un bon développeur est paresseux et délègue la répétition à l'ordinateur grâce à une **Boucle**.

La première boucle est `while`, qui signifie « TANT QUE » en anglais.
Elle ressemble beaucoup au `if`, mais au lieu de s'exécuter une seule fois, elle recommence en boucle TANT QUE sa condition reste vraie !
                    """.trimIndent(),
                    codeSnippet = """
# Un compteur qui démarre à 1
compteur = 1

while compteur <= 3:
    print(f"Tour numéro {compteur}")
    compteur = compteur + 1 # On augmente le compteur à chaque tour

print("La boucle est terminée !")
                    """.trimIndent(),
                    expectedOutput = """
Tour numéro 1
Tour numéro 2
Tour numéro 3
La boucle est terminée !
                    """.trimIndent(),
                    warning = "Si vous oubliez d'augmenter votre compteur (compteur = compteur + 1), la condition restera toujours vraie et votre programme tournera à l'infini (Boucle Infinie) jusqu'à saturer votre machine !"
                ),
                LessonSection(
                    title = "2. La boucle for et la fonction range()",
                    content = """
Quand on sait exactement à l'avance combien de fois on veut répéter une action, la boucle `for` est la reine incontestée de Python.
Pour répéter un nombre de fois précis, on l'associe à la fonction `range(debut, fin)`.

Règle cruciale de `range(1, 5)` :
La borne de fin est TOUJOURS exclue ! `range(1, 5)` va produire les nombres 1, 2, 3 et 4 (il s'arrête juste avant 5).
                    """.trimIndent(),
                    codeSnippet = """
# Répéter pour les valeurs de 1 à 4
for i in range(1, 5):
    print(f"Étape #{i}")

# Si on ne donne qu'un seul nombre, range commence automatiquement à 0 :
# range(3) va produire : 0, 1, 2
for i in range(3):
    print(f"Indice {i}")
                    """.trimIndent(),
                    expectedOutput = """
Étape #1
Étape #2
Étape #3
Étape #4
Indice 0
Indice 1
Indice 2
                    """.trimIndent()
                ),
                LessonSection(
                    title = "3. Contrôler la boucle : break et continue",
                    content = """
Parfois, pendant qu'une boucle tourne, un événement imprévu survient :
• `break` : ordonne à l'ordinateur d'INTERROMPRE IMMÉDIATEMENT la boucle et d'en sortir sur le champ.
• `continue` : ordonne de passer directement au tour suivant sans exécuter les lignes restantes du tour en cours.
                    """.trimIndent(),
                    codeSnippet = """
# Exemple avec break (s'arrêter dès qu'on trouve ce qu'on cherche)
for n in range(1, 10):
    if n == 4:
        print("Cible 4 trouvée ! Arrêt d'urgence avec break.")
        break
    print(f"Scan du nombre {n}...")
                    """.trimIndent(),
                    expectedOutput = """
Scan du nombre 1...
Scan du nombre 2...
Scan du nombre 3...
Cible 4 trouvée ! Arrêt d'urgence avec break.
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_06_1",
                    question = "Quelles valeurs sont produites par range(1, 4) ?",
                    codeSnippet = null,
                    options = listOf("1, 2, 3, 4", "1, 2, 3", "0, 1, 2, 3", "2, 3, 4"),
                    correctIndex = 1,
                    explanation = "La borne supérieure de range() est toujours exclue. range(1, 4) s'arrête avant 4 et génère donc 1, 2 et 3."
                ),
                QuizQuestion(
                    id = "q_06_2",
                    question = "Que fait l'instruction break à l'intérieur d'une boucle ?",
                    codeSnippet = null,
                    options = listOf(
                        "Elle met la boucle en pause 1 seconde",
                        "Elle stoppe net la boucle et sort immédiatement",
                        "Elle redémarre la boucle depuis le début",
                        "Elle efface la variable"
                    ),
                    correctIndex = 1,
                    explanation = "break stoppe prématurément et définitivement l'exécution de la boucle courante."
                )
            )
        ),

        // =========================================================================
        // LEÇON 7 : LES LISTES ET TUPLES
        // STRICT : PRÉREQUIS VARIABLES, CONDITIONS, BOUCLES
        // AUCUN DICTIONNAIRE (CH 8), AUCUNE FONCTION DEF (CH 10)
        // =========================================================================
        PythonLesson(
            id = "py_07_lists_tuples",
            number = 7,
            title = "7. Ranger Plusieurs Éléments : Listes & Tuples",
            subtitle = "Collections ordonnées, crochets [ ], indices dès 0, append, pop et slicing",
            level = PythonLevel.INTERMEDIAIRE_1,
            durationMinutes = 11,
            xpReward = 85,
            summary = "Découvrez comment regrouper des dizaines ou des milliers de valeurs dans une seule variable organisée au lieu d'éparpiller 50 variables différentes.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce qu'une Liste en Python ?",
                    content = """
Jusqu'ici, chaque variable ne pouvait contenir qu'UNE SEULE valeur (ex: `pseudo = "Anos"`).
Mais que faire si vous devez stocker les notes de 30 élèves, ou l'inventaire d'un joueur ?

Une **Liste** est un conteneur qui peut stocker plusieurs éléments ordonnés les uns après les autres.
On l'écrit avec des crochets `[` et `]`, et on sépare chaque élément par une virgule.
                    """.trimIndent(),
                    codeSnippet = """
# Une liste de textes
amis = ["Alice", "Bob", "Charlie"]

# Une liste de nombres
scores = [100, 250, 480, 720]

print("Ma liste d'amis :", amis)
print("Nombre d'amis dans la liste :", len(amis)) # len() donne la taille
                    """.trimIndent(),
                    expectedOutput = """
Ma liste d'amis : ['Alice', 'Bob', 'Charlie']
Nombre d'amis dans la liste : 3
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Récupérer un élément par son indice (Les indices commencent à 0)",
                    content = """
En informatique, on commence TOUJOURS à compter à partir de **zéro** !
• Le 1er élément est à l'indice `[0]`
• Le 2ème élément est à l'indice `[1]`
• Le 3ème élément est à l'indice `[2]`

Et Python a une astuce géniale : l'indice négatif `[-1]` donne directement le **dernier** élément de la liste !
                    """.trimIndent(),
                    codeSnippet = """
langages = ["Python", "Java", "C++", "JavaScript"]

print("Le premier :", langages[0])
print("Le deuxième :", langages[1])
print("Le tout dernier :", langages[-1])
                    """.trimIndent(),
                    expectedOutput = """
Le premier : Python
Le deuxième : Java
Le tout dernier : JavaScript
                    """.trimIndent(),
                    warning = "Si vous demandez langages[10] alors que la liste ne contient que 4 éléments, Python lève une erreur célèbre : IndexError: list index out of range !"
                ),
                LessonSection(
                    title = "3. Modifier une liste et la parcourir avec for",
                    content = """
Une liste est **mutable** (modifiable à tout moment) :
• `.append(element)` : ajoute un nouvel élément à la fin de la liste.
• `.pop()` : retire et renvoie le dernier élément.
Et pour parcourir chaque élément un par un, la boucle `for` est magique !
                    """.trimIndent(),
                    codeSnippet = """
panier = ["Pomme", "Banane"]
panier.append("Mangue") # On ajoute Mangue

# On parcourt chaque fruit avec la boucle for
for fruit in panier:
    print(f"J'achète : {fruit}")
                    """.trimIndent(),
                    expectedOutput = """
J'achète : Pomme
J'achète : Banane
J'achète : Mangue
                    """.trimIndent()
                ),
                LessonSection(
                    title = "4. Les Tuples (Des listes non modifiables)",
                    content = """
Un **Tuple** s'écrit entre parenthèses `(` et `)`.
Il fonctionne comme une liste, mais il est **IMMUABLE** : une fois créé, il est gravé dans la roche. On ne peut ni ajouter, ni supprimer, ni changer d'élément.
Cela sert à protéger des données fixes (comme des coordonnées GPS latitude/longitude).
                    """.trimIndent(),
                    codeSnippet = """
point_gps = (48.8566, 2.3522)
print("Latitude :", point_gps[0])
print("Longitude :", point_gps[1])
# point_gps[0] = 50.0 -> ERREUR ! Un tuple ne peut pas être modifié.
                    """.trimIndent(),
                    expectedOutput = """
Latitude : 48.8566
Longitude : 2.3522
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_07_1",
                    question = "Quel est l'indice du premier élément d'une liste en Python ?",
                    codeSnippet = null,
                    options = listOf("1", "0", "-1", "first"),
                    correctIndex = 1,
                    explanation = "En Python et dans la quasi-totalité des langages modernes, l'indexation commence toujours à 0."
                ),
                QuizQuestion(
                    id = "q_07_2",
                    question = "Quelle méthode permet d'ajouter un nouvel élément à la fin d'une liste ?",
                    codeSnippet = null,
                    options = listOf(".add()", ".insert_last()", ".append()", ".push()"),
                    correctIndex = 2,
                    explanation = "En Python, on utilise la méthode .append(nouvel_element) pour ajouter un élément à la fin d'une liste."
                )
            )
        ),

        // =========================================================================
        // LEÇON 8 : LES DICTIONNAIRES ET ENSEMBLES (SETS)
        // =========================================================================
        PythonLesson(
            id = "py_08_dicts_sets",
            number = 8,
            title = "8. Clés & Valeurs : Dictionnaires et Ensembles (Sets)",
            subtitle = "Retrouver une information par son nom, dict {cle: valeur} et élimination des doublons",
            level = PythonLevel.INTERMEDIAIRE_1,
            durationMinutes = 11,
            xpReward = 85,
            summary = "Découvrez les dictionnaires pour associer des étiquettes à des valeurs, et les ensembles pour garantir l'unicité sans doublon.",
            sections = listOf(
                LessonSection(
                    title = "1. Le Dictionnaire (dict) : Une étiquette pour chaque valeur",
                    content = """
Dans une liste, pour retrouver une valeur, on devait retenir son numéro d'indice (0, 1, 2...).
Dans la vraie vie, un dictionnaire associe un **mot** à sa **définition**.

En Python, un dictionnaire associe une **Clé** à une **Valeur** : `{clé: valeur}`.
On l'écrit avec des accolades `{` et `}`.
                    """.trimIndent(),
                    codeSnippet = """
joueur = {
    "pseudo": "Anos",
    "niveau": 42,
    "role": "Mage"
}

# Pour lire une valeur, on donne le nom de sa clé entre crochets :
print("Pseudo :", joueur["pseudo"])
print("Niveau :", joueur["niveau"])

# Pour modifier ou ajouter une nouvelle clé :
joueur["or"] = 500 # Ajoute une nouvelle paire clé/valeur
print("Inventaire mis à jour :", joueur)
                    """.trimIndent(),
                    expectedOutput = """
Pseudo : Anos
Niveau : 42
Inventaire mis à jour : {'pseudo': 'Anos', 'niveau': 42, 'role': 'Mage', 'or': 500}
                    """.trimIndent(),
                    tip = "Pour éviter une erreur si la clé n'existe pas, utilisez joueur.get('cle', valeur_par_defaut)."
                ),
                LessonSection(
                    title = "2. Les Ensembles (set) : L'interdiction des doublons",
                    content = """
Un `set` (ensemble) s'écrit aussi avec des accolades, mais sans deux-points : `{1, 2, 3}`.
Sa super-puissance ? **Il est impossible qu'un élément y soit présent deux fois !**
Dès que vous donnez des doublons à un set, il les élimine automatiquement.
                    """.trimIndent(),
                    codeSnippet = """
# Une liste avec plein de doublons
achats = ["Pain", "Lait", "Pain", "Pomme", "Lait"]

# On transforme en set pour supprimer les doublons :
achats_uniques = set(achats)
print("Sans aucun doublon :", achats_uniques)
                    """.trimIndent(),
                    expectedOutput = """
Sans aucun doublon : {'Pain', 'Lait', 'Pomme'}
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_08_1",
                    question = "Comment accède-t-on à la valeur associée à la clé 'age' dans le dictionnaire profil ?",
                    codeSnippet = null,
                    options = listOf("profil.age", "profil['age']", "profil(0)", "profil->age"),
                    correctIndex = 1,
                    explanation = "On utilise les crochets avec le nom de la clé entre guillemets : profil['age']."
                ),
                QuizQuestion(
                    id = "q_08_2",
                    question = "Que va contenir set([1, 2, 2, 3, 3, 3]) ?",
                    codeSnippet = null,
                    options = listOf("{1, 2, 2, 3, 3, 3}", "{1, 2, 3}", "Une erreur", "[1, 2, 3]"),
                    correctIndex = 1,
                    explanation = "Un ensemble (set) élimine automatiquement toutes les répétitions pour ne garder que des valeurs uniques."
                )
            )
        ),

        // =========================================================================
        // LEÇON 9 : COMPRÉHENSIONS DE LISTES
        // =========================================================================
        PythonLesson(
            id = "py_09_comprehensions",
            number = 9,
            title = "9. La Super-Syntaxe : Compréhensions de Listes",
            subtitle = "[x for x in liste if condition] : transformer et filtrer en 1 ligne",
            level = PythonLevel.INTERMEDIAIRE_1,
            durationMinutes = 9,
            xpReward = 85,
            summary = "Apprenez l'une des syntaxes les plus admirées de Python : créer, transformer et filtrer des listes entières en une seule ligne élégante.",
            sections = listOf(
                LessonSection(
                    title = "1. Le problème de la méthode classique",
                    content = """
Imaginez que vous ayez une liste de nombres et que vous vouliez calculer le carré de chacun.
Méthode classique vue jusqu'ici :
1. Créer une liste vide : `carres = []`
2. Faire une boucle `for n in nombres:`
3. Faire `carres.append(n * n)`

C'est 4 lignes de code pour une tâche très simple.
Python permet de faire cela en UNE SEULE LIGNE magique !
                    """.trimIndent(),
                    codeSnippet = """
nombres = [1, 2, 3, 4, 5]

# La compréhension de liste : [ce_qu_on_veut for element in liste]
carres = [n * n for n in nombres]
print("Les carrés :", carres)
                    """.trimIndent(),
                    expectedOutput = """
Les carrés : [1, 4, 9, 16, 25]
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Filtrer avec une condition if à la fin",
                    content = """
Vous pouvez ajouter un `if` à la fin de la compréhension pour ne garder que certains éléments !
                    """.trimIndent(),
                    codeSnippet = """
notes = [8, 14, 9, 17, 12, 19]

# On ne garde que les notes supérieures ou égales à 10 :
admis = [n for n in notes if n >= 10]
print("Notes admises :", admis)
                    """.trimIndent(),
                    expectedOutput = """
Notes admises : [14, 17, 12, 19]
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_09_1",
                    question = "Que produit l'expression : [x * 2 for x in [1, 2, 3]] ?",
                    codeSnippet = null,
                    options = listOf("[1, 2, 3, 1, 2, 3]", "[2, 4, 6]", "[2, 2, 2]", "[1, 4, 9]"),
                    correctIndex = 1,
                    explanation = "Chaque élément x de la liste [1, 2, 3] est multiplié par 2, ce qui donne [2, 4, 6]."
                )
            )
        ),

        // =========================================================================
        // LEÇON 10 : LES FONCTIONS FONDAMENTALES (DEF & RETURN)
        // =========================================================================
        PythonLesson(
            id = "py_10_functions_basics",
            number = 10,
            title = "10. Créer ses Propres Ordres : Les Fonctions (def)",
            subtitle = "def, paramètres d'entrée, mot-clé return et réutilisabilité",
            level = PythonLevel.INTERMEDIAIRE_1,
            durationMinutes = 10,
            xpReward = 90,
            summary = "Créez vos propres instructions personnalisées, donnez-leur des paramètres et faites-leur renvoyer des résultats réutilisables dans tout votre code.",
            sections = listOf(
                LessonSection(
                    title = "1. Qu'est-ce qu'une Fonction ?",
                    content = """
Depuis le début, nous utilisons des fonctions créées par les inventeurs de Python : `print()`, `input()`, `len()`, `int()`...
Mais la vraie magie commence quand vous fabriquez VOS PROPRES fonctions !

Une fonction, c'est comme une mini-machine ou un nouveau verbe d'action :
1. Elle a un **nom**.
2. Elle peut recevoir des ingrédients en entrée (les **paramètres**).
3. Elle fait des calculs ou actions.
4. Elle peut renvoyer un résultat avec le mot-clé `return`.
                    """.trimIndent(),
                    codeSnippet = """
# On définit notre machine avec le mot-clé 'def' :
def saluer(prenom):
    print(f"Bonjour {prenom} ! Bienvenue sur Anos py.")

# Maintenant, on peut appeler notre nouvelle commande autant de fois qu'on veut :
saluer("Alice")
saluer("Bob")
                    """.trimIndent(),
                    expectedOutput = """
Bonjour Alice ! Bienvenue sur Anos py.
Bonjour Bob ! Bienvenue sur Anos py.
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Le mot-clé return pour renvoyer un résultat",
                    content = """
`print()` se contente d'afficher du texte à l'écran, mais ne renvoie rien au reste du programme.
Pour qu'une fonction produise un résultat qu'on peut ranger dans une variable ou réutiliser dans un calcul, on utilise `return` !
                    """.trimIndent(),
                    codeSnippet = """
def calculer_aire(largeur, hauteur):
    aire = largeur * hauteur
    return aire # On renvoie le résultat

# On appelle la fonction et on stocke le résultat dans une variable :
resultat = calculer_aire(5, 4)
print(f"L'aire du rectangle est de : {resultat} m²")
                    """.trimIndent(),
                    expectedOutput = """
L'aire du rectangle est de : 20 m²
                    """.trimIndent(),
                    tip = "Dès que Python lit un return, la fonction s'arrête immédiatement et renvoie la valeur spécifiée."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_10_1",
                    question = "Quel mot-clé utilise-t-on pour déclarer une fonction en Python ?",
                    codeSnippet = null,
                    options = listOf("function", "def", "create", "fun"),
                    correctIndex = 1,
                    explanation = "En Python, on utilise toujours le mot-clé 'def' (pour define) pour créer une fonction."
                ),
                QuizQuestion(
                    id = "q_10_2",
                    question = "Quelle instruction permet à une fonction de renvoyer une valeur au code qui l'a appelée ?",
                    codeSnippet = null,
                    options = listOf("print", "give", "return", "send"),
                    correctIndex = 2,
                    explanation = "return termine la fonction et renvoie le résultat calculé."
                )
            )
        )
    )
}
