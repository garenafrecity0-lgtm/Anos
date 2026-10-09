package com.example.data.generator

import com.example.data.model.LessonSection
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.QuizQuestion

object PythonLessonsIntermediate2 {
    val lessons: List<PythonLesson> = listOf(
        // Leçon 11 : *args et **kwargs
        PythonLesson(
            id = "py_11_args_kwargs",
            number = 11,
            title = "Arguments Flexibles : *args & **kwargs",
            subtitle = "Tuples et dictionnaires d'arguments dynamiques, déballage (unpacking)",
            level = PythonLevel.INTERMEDIAIRE_2,
            durationMinutes = 9,
            xpReward = 95,
            summary = "Créez des fonctions à signature variable et maîtrisez le déballage d'arguments.",
            sections = listOf(
                LessonSection(
                    title = "1. *args : Arguments positionnels variables",
                    content = """
Le préfixe `*` devant un paramètre (par convention `*args`) permet de recevoir un nombre indéterminé d'arguments positionnels sous la forme d'un **tuple**.
                    """.trimIndent(),
                    codeSnippet = """
def additionner_tout(*nombres):
    # nombres est un tuple
    print(f"Reçu {len(nombres)} nombres : {nombres}")
    return sum(nombres)

print("Total :", additionner_tout(10, 20, 30, 40))
                    """.trimIndent(),
                    expectedOutput = """
Reçu 4 nombres : (10, 20, 30, 40)
Total : 100
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. **kwargs et Déballage d'arguments",
                    content = """
Le préfixe `**` devant un paramètre (convention `**kwargs` pour keyword arguments) capture tous les arguments nommés sous la forme d'un **dictionnaire**.
Vous pouvez aussi utiliser `*` et `**` lors de l'appel d'une fonction pour déballer une liste ou un dictionnaire !
                    """.trimIndent(),
                    codeSnippet = """
def creer_utilisateur(pseudo, **options):
    print(f"Création de {pseudo} :")
    for cle, val in options.items():
        print(f"  • {cle} = {val}")

creer_utilisateur("Anos", role="Admin", theme="Dark", niveau=99)

# Déballage à l'appel
coords = [10, 25]
def deplacer(x, y):
    print(f"Position: x={x}, y={y}")

deplacer(*coords) # déballe la liste en x=10, y=25
                    """.trimIndent(),
                    expectedOutput = """
Création de Anos :
  • role = Admin
  • theme = Dark
  • niveau = 99
Position: x=10, y=25
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_11_1",
                    question = "Sous quel type de structure de données *args est-il reçu dans la fonction ?",
                    codeSnippet = null,
                    options = listOf("Une liste", "Un tuple", "Un dictionnaire", "Un ensemble (set)"),
                    correctIndex = 1,
                    explanation = "*args regroupe les arguments positionnels excédentaires sous forme d'un tuple immuable."
                ),
                QuizQuestion(
                    id = "q_11_2",
                    question = "Sous quel type de structure **kwargs est-il reçu dans la fonction ?",
                    codeSnippet = null,
                    options = listOf("Un dictionnaire (dict)", "Un tuple", "Une liste", "Une chaîne"),
                    correctIndex = 0,
                    explanation = "**kwargs capture les arguments nommés sous forme de dictionnaire clé-valeur."
                )
            )
        ),

        // Leçon 12 : Scope LEGB & Lambdas
        PythonLesson(
            id = "py_12_scope_lambdas",
            number = 12,
            title = "Portée LEGB, Closures & Fonctions Lambda",
            subtitle = "Local, Enclosing, Global, Built-in et fonctions anonymes",
            level = PythonLevel.INTERMEDIAIRE_2,
            durationMinutes = 10,
            xpReward = 95,
            summary = "Comprenez la résolution des variables en Python et écrivez des fonctions lambda concises.",
            sections = listOf(
                LessonSection(
                    title = "1. La Règle de Résolution de Portée LEGB",
                    content = """
Quand Python cherche la valeur d'une variable, il inspecte 4 niveaux dans l'ordre strict LEGB :
1. **L**ocal : Variables créées dans la fonction courante.
2. **E**nclosing : Fonctions englobantes (dans le cas de fonctions imbriquées / closures).
3. **G**lobal : Variables au niveau du fichier module (modifiable avec le mot-clé `global`).
4. **B**uilt-in : Noms réservés intégrés à Python (`len`, `print`, `int`, etc.).
                    """.trimIndent(),
                    codeSnippet = """
compteur_global = 10

def incrementer():
    global compteur_global
    compteur_global += 1

incrementer()
print("Compteur global :", compteur_global)
                    """.trimIndent(),
                    expectedOutput = """
Compteur global : 11
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Les Fonctions Lambda et Fonctions d'Ordre Supérieur",
                    content = """
Une fonction `lambda` est une fonction anonyme définie en une seule ligne d'expression :
`lambda param1, param2: expression`
Elle est idéale comme fonction de tri ou avec `map()` et `filter()`.
                    """.trimIndent(),
                    codeSnippet = """
carre = lambda x: x ** 2
print("Carré de 5 :", carre(5))

# Tri d'une liste de tuples par le deuxième élément
joueurs = [("Alice", 85), ("Bob", 98), ("Charlie", 72)]
joueurs_tries = sorted(joueurs, key=lambda j: j[1], reverse=True)
print("Classement :", joueurs_tries)
                    """.trimIndent(),
                    expectedOutput = """
Carré de 5 : 25
Classement : [('Bob', 98), ('Alice', 85), ('Charlie', 72)]
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_12_1",
                    question = "Quel est l'ordre exact de recherche des variables selon la règle LEGB ?",
                    codeSnippet = null,
                    options = listOf(
                        "Local -> Enclosing -> Global -> Built-in",
                        "Literal -> Element -> Group -> Base",
                        "Global -> Local -> Enclosing -> Built-in",
                        "Local -> Global -> Enclosing -> Built-in"
                    ),
                    correctIndex = 0,
                    explanation = "LEGB signifie Local, Enclosing, Global, Built-in. Python recherche toujours du périmètre le plus restreint au plus large."
                ),
                QuizQuestion(
                    id = "q_12_2",
                    question = "Que fait lambda x, y: x + y ?",
                    codeSnippet = null,
                    options = listOf(
                        "Elle définit une fonction anonyme qui additionne x et y",
                        "Elle compare x et y",
                        "C'est une macro de préprocesseur",
                        "Une variable booléenne"
                    ),
                    correctIndex = 0,
                    explanation = "lambda crée une fonction anonyme prenant x et y en paramètres et renvoyant le résultat de x + y."
                )
            )
        ),

        // Leçon 13 : Exceptions
        PythonLesson(
            id = "py_13_exceptions",
            number = 13,
            title = "Gestion Robuste des Exceptions",
            subtitle = "try, except, else, finally et lever des erreurs avec raise",
            level = PythonLevel.INTERMEDIAIRE_2,
            durationMinutes = 10,
            xpReward = 100,
            summary = "Empêchez vos applications de crasher en interceptant proprement les erreurs à l'exécution.",
            sections = listOf(
                LessonSection(
                    title = "1. Le bloc try / except / else / finally",
                    content = """
Les erreurs d'exécution en Python lèvent des **Exceptions**. Sans interception, elles interrompent le programme.
Le bloc protecteur complet se compose de :
• `try` : le code susceptible d'échouer.
• `except NomErreur as e` : bloc de secours si l'erreur survient.
• `else` : s'exécute uniquement si **aucune** exception n'a été levée dans le `try`.
• `finally` : s'exécute **toujours**, qu'il y ait eu erreur ou non (idéal pour libérer des ressources).
                    """.trimIndent(),
                    codeSnippet = """
def diviser_securise(a, b):
    try:
        resultat = a / b
    except ZeroDivisionError:
        print("⚠️ Erreur : Division par zéro impossible !")
        return None
    except TypeError as e:
        print(f"⚠️ Erreur de type : {e}")
        return None
    else:
        print("✅ Calcul réussi sans incident !")
        return resultat
    finally:
        print("🔄 Opération terminée.")

print("Résultat :", diviser_securise(10, 2))
diviser_securise(10, 0)
                    """.trimIndent(),
                    expectedOutput = """
✅ Calcul réussi sans incident !
🔄 Opération terminée.
Résultat : 5.0
⚠️ Erreur : Division par zéro impossible !
🔄 Opération terminée.
                    """.trimIndent(),
                    tip = "N'utilisez jamais un 'except:' nu sans type d'exception ! Cela intercepterait même KeyboardInterrupt (Ctrl+C), empêchant l'arrêt propre du programme."
                ),
                LessonSection(
                    title = "2. Lever des Exceptions Personnalisées (raise)",
                    content = """
Vous pouvez déclencher vos propres exceptions avec le mot-clé `raise`.
Pour créer vos propres types d'erreurs, héritez simplement de la classe de base `Exception`.
                    """.trimIndent(),
                    codeSnippet = """
class SoldeInsuffisantError(Exception):
    \"\"\"Exception levée quand un retrait dépasse le solde disponible.\"\"\"
    pass

def retirer_argent(solde, montant):
    if montant > solde:
        raise SoldeInsuffisantError(f"Solde ({solde}€) inférieur au retrait ({montant}€)")
    return solde - montant

try:
    retirer_argent(50, 100)
except SoldeInsuffisantError as e:
    print(f"Alerte bancaire : {e}")
                    """.trimIndent(),
                    expectedOutput = """
Alerte bancaire : Solde (50€) inférieur au retrait (100€)
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_13_1",
                    question = "Quand le bloc 'else' d'un bloc try/except est-il exécuté ?",
                    codeSnippet = null,
                    options = listOf(
                        "Toujours, comme le finally",
                        "Uniquement si AUCUNE exception n'a été levée dans le try",
                        "Uniquement si une exception a été attrapée",
                        "Si le bloc finally échoue"
                    ),
                    correctIndex = 1,
                    explanation = "La clause else d'un try s'exécute uniquement si le bloc try a réussi sans lever la moindre exception."
                ),
                QuizQuestion(
                    id = "q_13_2",
                    question = "Quel mot-clé permet de déclencher manuellement une exception en Python ?",
                    codeSnippet = null,
                    options = listOf("throw", "raise", "error", "trigger"),
                    correctIndex = 1,
                    explanation = "En Python, on utilise le mot-clé 'raise' (et non 'throw' comme en Java ou JavaScript)."
                )
            )
        ),

        // Leçon 14 : Modules & Bibliothèque Standard
        PythonLesson(
            id = "py_14_modules_std",
            number = 14,
            title = "Modules & Bibliothèque Standard",
            subtitle = "import, math, random, datetime, json et pip / virtualenv",
            level = PythonLevel.INTERMEDIAIRE_2,
            durationMinutes = 9,
            xpReward = 95,
            summary = "Exploitez la devise de Python : 'Batteries Included' avec les modules de la bibliothèque standard.",
            sections = listOf(
                LessonSection(
                    title = "1. Importer et Organiser les Modules",
                    content = """
Un module est un fichier `.py` contenant du code réutilisable.
Syntaxe d'importation :
• `import math` -> `math.sqrt(16)`
• `from random import randint, choice` -> `choice(['A', 'B'])`
• `import datetime as dt` (avec alias)
                    """.trimIndent(),
                    codeSnippet = """
import math
from random import choice, randint

# Math
print("Racine carrée de 144 :", math.sqrt(144))
print("Pi arrondi :", round(math.pi, 4))

# Random
lancer_de = randint(1, 6)
carte = choice(["As de Coeur", "Roi de Pique", "Dame de Carreau"])
print(f"Dé: {lancer_de} | Carte tirée: {carte}")
                    """.trimIndent(),
                    expectedOutput = """
Racine carrée de 144 : 12.0
Pi arrondi : 3.1416
Dé: 5 | Carte tirée: As de Coeur
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. La condition if __name__ == '__main__':",
                    content = """
Cette construction est cruciale :
Quand un fichier est exécuté directement par Python, sa variable magique `__name__` vaut `"__main__"`.
S'il est importé par un autre fichier, `__name__` prend le nom du fichier.
Cela permet d'avoir du code qui ne s'exécute que lors d'un lancement direct (ex: tests unitaires).
                    """.trimIndent(),
                    codeSnippet = """
def saluer(nom):
    return f"Bonjour {nom} !"

if __name__ == "__main__":
    print(saluer("Anos"))
    print("Ce message s'affiche uniquement si ce fichier est le script principal !")
                    """.trimIndent(),
                    expectedOutput = """
Bonjour Anos !
Ce message s'affiche uniquement si ce fichier est le script principal !
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_14_1",
                    question = "Que vaut la variable spéciale __name__ quand un script est exécuté directement ?",
                    codeSnippet = null,
                    options = listOf("'__main__'", "'root'", "'default'", "Le nom du fichier"),
                    correctIndex = 0,
                    explanation = "Lorsqu'un script Python est exécuté en tant que point d'entrée, Python définit sa variable spéciale __name__ à '__main__'."
                ),
                QuizQuestion(
                    id = "q_14_2",
                    question = "Quel gestionnaire officiel de paquets permet d'installer des bibliothèques tierces en Python ?",
                    codeSnippet = null,
                    options = listOf("npm", "pip", "cargo", "gem"),
                    correctIndex = 1,
                    explanation = "pip (Pip Installs Packages) est l'outil officiel pour installer des modules depuis l'index PyPI."
                )
            )
        ),

        // Leçon 15 : Fichiers & Context Managers
        PythonLesson(
            id = "py_15_files_context",
            number = 15,
            title = "Manipulation de Fichiers & Context Managers",
            subtitle = "with open(), modes 'r', 'w', 'a', encodage UTF-8 et JSON",
            level = PythonLevel.INTERMEDIAIRE_2,
            durationMinutes = 10,
            xpReward = 100,
            summary = "Lisez et écrivez des fichiers texte et JSON en toute sécurité grâce à l'instruction with.",
            sections = listOf(
                LessonSection(
                    title = "1. L'instruction with open() (Context Manager)",
                    content = """
L'instruction `with` garantit que le fichier sera automatiquement et proprement fermé à la fin du bloc, même si une exception survient au milieu de la lecture !
Modes d'ouverture :
• `'r'` : lecture (read, par défaut).
• `'w'` : écriture (écrase le contenu existant).
• `'a'` : ajout à la fin (append).
Spécifiez toujours `encoding="utf-8"` pour éviter les soucis d'accents.
                    """.trimIndent(),
                    codeSnippet = """
import json

# Simulation de manipulation de données
donnees_app = {
    "app": "Anos py",
    "version": "2.0",
    "modules": ["Bases", "POO", "Expert"]
}

# Sérialisation JSON
json_texte = json.dumps(donnees_app, indent=2, ensure_ascii=False)
print("Flux JSON généré :\n", json_texte)

# Désérialisation
objet_reconstruit = json.loads(json_texte)
print("Nom de l'app :", objet_reconstruit["app"])
                    """.trimIndent(),
                    expectedOutput = """
Flux JSON généré :
 {
  "app": "Anos py",
  "version": "2.0",
  "modules": [
    "Bases",
    "POO",
    "Expert"
  ]
}
Nom de l'app : Anos py
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_15_1",
                    question = "Quel est le grand avantage de l'instruction 'with open(...)' ?",
                    codeSnippet = null,
                    options = listOf(
                        "Le fichier est plus rapide à charger",
                        "Le fichier est automatiquement fermé dès la sortie du bloc, même en cas d'erreur",
                        "Elle crypte les données sur le disque",
                        "Elle permet de modifier les fichiers sans permission"
                    ),
                    correctIndex = 1,
                    explanation = "Le context manager 'with' appelle automatiquement la méthode .close() à la sortie du bloc, prévenant les fuites de descripteurs de fichiers."
                ),
                QuizQuestion(
                    id = "q_15_2",
                    question = "Quel mode d'ouverture devez-vous utiliser pour ajouter du texte à la fin d'un fichier existant sans l'écraser ?",
                    codeSnippet = null,
                    options = listOf("'w'", "'r'", "'a'", "'x'"),
                    correctIndex = 2,
                    explanation = "Le mode 'a' (append) ajoute les nouvelles données à la fin du fichier sans toucher au contenu existant."
                )
            )
        )
    )
}
