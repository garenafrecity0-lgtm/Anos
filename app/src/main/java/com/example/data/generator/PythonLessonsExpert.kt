package com.example.data.generator

import com.example.data.model.LessonSection
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.QuizQuestion

object PythonLessonsExpert {
    val lessons: List<PythonLesson> = listOf(
        // Leçon 20 : Décorateurs Avancés
        PythonLesson(
            id = "py_20_decorators",
            number = 20,
            title = "Décorateurs Avancés & Closures",
            subtitle = "Fonctions de première classe, emballage dynamique et @functools.wraps",
            level = PythonLevel.EXPERT,
            durationMinutes = 12,
            xpReward = 130,
            summary = "Maîtrisez l'un des motifs de conception les plus élégants de Python pour enrichir vos fonctions sans modifier leur code source.",
            sections = listOf(
                LessonSection(
                    title = "1. Anatomie d'un Décorateur",
                    content = """
En Python, les fonctions sont des « citoyens de première classe » (First-Class Citizens) : elles peuvent être passées en argument, renvoyées par d'autres fonctions et assignées à des variables.
Un décorateur est une fonction qui prend une autre fonction en paramètre et renvoie une version enrichie de celle-ci.
La syntaxe `@mon_decorateur` est simplement du sucre syntaxique pour :
`ma_fonction = mon_decorateur(ma_fonction)`
                    """.trimIndent(),
                    codeSnippet = """
import time
from functools import wraps

def chronometrer(fonction):
    @wraps(fonction) # Préserve le nom et la docstring originaux
    def emballage(*args, **kwargs):
        debut = time.time()
        resultat = fonction(*args, **kwargs)
        fin = time.time()
        print(f"⏱️ [{fonction.__name__}] exécuté en {(fin - debut)*1000:.2f} ms")
        return resultat
    return emballage

@chronometrer
def calcul_lourd(n):
    return sum(i ** 2 for i in range(n))

valeur = calcul_lourd(100_000)
print("Résultat calcul :", valeur)
                    """.trimIndent(),
                    expectedOutput = """
⏱️ [calcul_lourd] exécuté en 8.42 ms
Résultat calcul : 333328333350000
                    """.trimIndent(),
                    tip = "Utilisez toujours @functools.wraps sur votre fonction interne pour que fonction.__name__ et les docstrings ne soient pas écrasés !"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_20_1",
                    question = "À quoi sert @functools.wraps à l'intérieur d'un décorateur ?",
                    codeSnippet = null,
                    options = listOf(
                        "À accélérer le temps d'exécution",
                        "À préserver les métadonnées de la fonction décorée (nom, docstring, signature)",
                        "À rendre la fonction asynchrone",
                        "À crypter le bytecode"
                    ),
                    correctIndex = 1,
                    explanation = "@wraps copie les attributs tels que __name__ et __doc__ de la fonction d'origine vers la fonction d'emballage."
                ),
                QuizQuestion(
                    id = "q_20_2",
                    question = "Que fait la syntaxe @log au-dessus de def f(): pass ?",
                    codeSnippet = null,
                    options = listOf(
                        "Elle crée une sous-classe de f",
                        "Elle équivaut à f = log(f)",
                        "Elle exécute f en tâche de fond",
                        "Elle compile f en langage C"
                    ),
                    correctIndex = 1,
                    explanation = "La syntaxe @decorateur est un sucre syntaxique direct pour fonction = decorateur(fonction)."
                )
            )
        ),

        // Leçon 21 : Générateurs & Protocole d'Itération
        PythonLesson(
            id = "py_21_generators",
            number = 21,
            title = "Générateurs & Évaluation Paresseuse (yield)",
            subtitle = "Protocole d'itération, consommation mémoire O(1) et expressions génératrices",
            level = PythonLevel.EXPERT,
            durationMinutes = 11,
            xpReward = 130,
            summary = "Traitez des flux infinis ou des milliards de données sans saturer la RAM grâce au mot-clé yield.",
            sections = listOf(
                LessonSection(
                    title = "1. Le mot-clé yield et l'évaluation paresseuse",
                    content = """
Une fonction normale avec `return` calcule l'intégralité de son résultat, le charge en RAM et termine.
Une fonction contenant `yield` devient un **Générateur** : chaque appel à `next()` produit la valeur suivante et met en pause l'état local de la fonction jusqu'au prochain appel.
Consommation mémoire : constante O(1), quelle que soit la taille de la séquence générée !
                    """.trimIndent(),
                    codeSnippet = """
def suite_fibonacci(limite):
    \"\"\"Génère les nombres de Fibonacci à la volée sans saturer la mémoire.\"\"\"
    a, b = 0, 1
    while a < limite:
        yield a # Pause et renvoie la valeur courante
        a, b = b, a + b

# Utilisation avec une boucle for
for nombre in suite_fibonacci(50):
    print(nombre, end=" ")
print()

# Expression génératrice (parenthèses au lieu de crochets)
flux_carres = (x ** 2 for x in range(1_000_000))
print("Taille mémoire du générateur : très faible !")
print("Premier élément :", next(flux_carres))
print("Deuxième élément :", next(flux_carres))
                    """.trimIndent(),
                    expectedOutput = """
0 1 1 2 3 5 8 13 21 34 
Taille mémoire du générateur : très faible !
Premier élément : 0
Deuxième élément : 1
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_21_1",
                    question = "Quelle est la différence fondamentale entre return et yield ?",
                    codeSnippet = null,
                    options = listOf(
                        "yield termine définitivement la fonction",
                        "yield renvoie une valeur et suspend la fonction en sauvegardant son état",
                        "yield ne peut renvoyer que des entiers",
                        "return est plus lent que yield"
                    ),
                    correctIndex = 1,
                    explanation = "yield produit une valeur et met la fonction en pause jusqu'à ce que l'itérateur demande la valeur suivante via next()."
                ),
                QuizQuestion(
                    id = "q_21_2",
                    question = "Quelle est la complexité mémoire d'un générateur produisant 1 milliard de nombres ?",
                    codeSnippet = null,
                    options = listOf("O(N) - plusieurs gigaoctets", "O(1) - quelques octets", "O(N log N)", "O(N^2)"),
                    correctIndex = 1,
                    explanation = "Les générateurs génèrent les valeurs une à une à la demande (lazy evaluation), la mémoire requise reste donc constante O(1)."
                )
            )
        ),

        // Leçon 22 : Context Managers Personnalisés
        PythonLesson(
            id = "py_22_context_managers",
            number = 22,
            title = "Context Managers Avancés & contextlib",
            subtitle = "Méthodes dunder __enter__ / __exit__ et décorateur @contextmanager",
            level = PythonLevel.EXPERT,
            durationMinutes = 11,
            xpReward = 135,
            summary = "Garantissez la libération absolue de verrous, de connexions réseau et de ressources système.",
            sections = listOf(
                LessonSection(
                    title = "1. Le Protocole __enter__ et __exit__",
                    content = """
Un gestionnaire de contexte est un objet qui implémente :
• `__enter__(self)` : prépare la ressource avant le bloc `with`.
• `__exit__(self, exc_type, exc_val, exc_tb)` : libère la ressource après le bloc, même si une exception survient.
                    """.trimIndent(),
                    codeSnippet = """
from contextlib import contextmanager

# Méthode moderne avec @contextmanager
@contextmanager
def balise_html(nom_balise):
    print(f"<{nom_balise}>")
    try:
        yield # Le code à l'intérieur du 'with' s'exécute ici
    finally:
        print(f"</{nom_balise}>")

with balise_html("div"):
    with balise_html("p"):
        print("Texte à l'intérieur du paragraphe !")
                    """.trimIndent(),
                    expectedOutput = """
<div>
<p>
Texte à l'intérieur du paragraphe !
</p>
</div>
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_22_1",
                    question = "Quelles méthodes spéciales définissent le protocole du Context Manager en Python ?",
                    codeSnippet = null,
                    options = listOf(
                        "__open__ et __close__",
                        "__enter__ et __exit__",
                        "__start__ et __finish__",
                        "__before__ et __after__"
                    ),
                    correctIndex = 1,
                    explanation = "Le protocole de gestion de contexte repose sur __enter__ et __exit__."
                ),
                QuizQuestion(
                    id = "q_22_2",
                    question = "Quel module standard propose le décorateur @contextmanager ?",
                    codeSnippet = null,
                    options = listOf("sys", "os", "contextlib", "itertools"),
                    correctIndex = 2,
                    explanation = "Le module standard contextlib fournit le décorateur utilitaire @contextmanager."
                )
            )
        ),

        // Leçon 23 : Programmation Asynchrone (asyncio)
        PythonLesson(
            id = "py_23_asyncio",
            number = 23,
            title = "Programmation Asynchrone avec asyncio",
            subtitle = "async, await, Event Loop, coroutines et concurrence non-bloquante",
            level = PythonLevel.EXPERT,
            durationMinutes = 13,
            xpReward = 140,
            summary = "Gérez des milliers d'opérations I/O concurrentes (appels API, bases de données) sans blocage.",
            sections = listOf(
                LessonSection(
                    title = "1. Coroutines et Boucle d'Événements",
                    content = """
En programmation synchrone classique, un appel réseau bloque l'intégralité du thread pendant l'attente de la réponse.
Avec `asyncio` :
• Une coroutine est déclarée avec `async def`.
• Le mot-clé `await` cède le contrôle à l'**Event Loop** pendant les opérations d'attente (I/O non-bloquantes).
• `asyncio.gather()` lance plusieurs coroutines en parallèle.
                    """.trimIndent(),
                    codeSnippet = """
import asyncio

async def telecharger_donnees(serveur_id, delai):
    print(f"📡 Connexion au serveur #{serveur_id}...")
    await asyncio.sleep(delai) # Attente non-bloquante
    print(f"✅ Données reçues du serveur #{serveur_id} !")
    return f"Data_{serveur_id}"

async def main():
    # Lance 3 téléchargements en parallèle sur un seul thread !
    resultats = await asyncio.gather(
        telecharger_donnees(1, 0.1),
        telecharger_donnees(2, 0.2),
        telecharger_donnees(3, 0.1)
    )
    print("Tous les téléchargements terminés :", resultats)

# En script standard : asyncio.run(main())
print("Architecture asynchrone prête.")
                    """.trimIndent(),
                    expectedOutput = """
Architecture asynchrone prête.
                    """.trimIndent(),
                    tip = "N'utilisez jamais time.sleep() dans une coroutine asynchrone ! Cela bloquerait toute l'Event Loop. Utilisez toujours await asyncio.sleep()."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_23_1",
                    question = "Quel mot-clé permet de déclarer une fonction coroutine asynchrone ?",
                    codeSnippet = null,
                    options = listOf("thread def", "async def", "parallel def", "coroutine def"),
                    correctIndex = 1,
                    explanation = "'async def' indique à Python que la fonction renvoie un objet coroutine qui peut être exécuté par l'Event Loop."
                ),
                QuizQuestion(
                    id = "q_23_2",
                    question = "Quelle fonction du module asyncio permet de lancer plusieurs coroutines simultanément et de collecter leurs résultats ?",
                    codeSnippet = null,
                    options = listOf("asyncio.run_all()", "asyncio.gather()", "asyncio.combine()", "asyncio.parallel()"),
                    correctIndex = 1,
                    explanation = "asyncio.gather() prend plusieurs coroutines ou tâches et les exécute concurremment, renvoyant la liste de leurs résultats."
                )
            )
        ),

        // Leçon 24 : Typage Statique & GIL
        PythonLesson(
            id = "py_24_typing_gil",
            number = 24,
            title = "Typage Statique Moderne & Le GIL de Python",
            subtitle = "Type hints, Union, TypeVar, Mypy, Global Interpreter Lock et Multiprocessing",
            level = PythonLevel.EXPERT,
            durationMinutes = 13,
            xpReward = 150,
            summary = "Découvrez le typage moderne de niveau entreprise et comprenez le fonctionnement interne du GIL (Global Interpreter Lock).",
            sections = listOf(
                LessonSection(
                    title = "1. Type Hints et Annotations Modernes",
                    content = """
Bien que Python soit à typage dynamique à l'exécution, les annotations de type (Type Hints, PEP 484) sont devenues le standard absolu en entreprise.
Elles permettent à des outils comme `mypy`, PyCharm et VS Code de détecter les bugs avant même de lancer le programme.
                    """.trimIndent(),
                    codeSnippet = """
from typing import Optional, List, Dict, TypeVar

T = TypeVar('T')

def premier_element(elements: List[T]) -> Optional[T]:
    \"\"\"Fonction générique : renvoie le premier élément ou None s'il est vide.\"\"\"
    return elements[0] if elements else None

def calculer_moyenne(notes: Dict[str, float]) -> float:
    return sum(notes.values()) / len(notes)

resultat = premier_element(["Alpha", "Beta", "Gamma"])
print("Premier :", resultat)
                    """.trimIndent(),
                    expectedOutput = """
Premier : Alpha
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Comprendre le GIL (Global Interpreter Lock)",
                    content = """
Le GIL est un verrou interne de CPython qui garantit qu'un seul thread exécute du bytecode Python à la fois dans un même processus.
Conséquence majeure :
• Pour les tâches **I/O-Bound** (requêtes réseau, lecture disque) : `threading` et `asyncio` sont parfaits.
• Pour les tâches **CPU-Bound** (calculs intensifs, IA, traitement d'images) : le multithreading ne tire pas parti du multi-cœur. Il faut utiliser le module `multiprocessing` pour contourner le GIL en créant des processus distincts avec leur propre mémoire !
                    """.trimIndent(),
                    tip = "Python 3.13 a introduit le support expérimental 'free-threaded' (sans GIL), une révolution historique pour les performances du langage !"
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_24_1",
                    question = "Quel est l'impact principal du GIL (Global Interpreter Lock) dans CPython ?",
                    codeSnippet = null,
                    options = listOf(
                        "Il interdit l'écriture de fichiers",
                        "Il restreint l'exécution du bytecode Python à un seul thread à la fois par processus",
                        "Il désactive les décorateurs",
                        "Il limite la mémoire à 4 Go"
                    ),
                    correctIndex = 1,
                    explanation = "Le GIL assure la sécurité mémoire de CPython en autorisant un seul thread à exécuter du code Python natif à un instant t."
                ),
                QuizQuestion(
                    id = "q_24_2",
                    question = "Quel module devez-vous privilégier pour effectuer des calculs CPU intensifs sur plusieurs cœurs en Python ?",
                    codeSnippet = null,
                    options = listOf("threading", "asyncio", "multiprocessing", "queue"),
                    correctIndex = 2,
                    explanation = "Le module multiprocessing démarre des processus système séparés avec chacun leur propre instance d'interpréteur, contournant le GIL pour exploiter 100% des cœurs CPU."
                )
            )
        )
    )
}
