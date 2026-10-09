package com.example.data.generator

import com.example.data.model.LessonSection
import com.example.data.model.PythonLesson
import com.example.data.model.PythonLevel
import com.example.data.model.QuizQuestion

object PythonLessonsOop {
    val lessons: List<PythonLesson> = listOf(
        // Leçon 16 : Classes & Objets
        PythonLesson(
            id = "py_16_classes",
            number = 16,
            title = "Programmation Orientée Objet : Classes & Objets",
            subtitle = "class, __init__, paramètre self, attributs d'instance et de classe",
            level = PythonLevel.AVANCE,
            durationMinutes = 11,
            xpReward = 110,
            summary = "Passez au paradigme objet : modélisez des entités réelles avec des classes et des méthodes.",
            sections = listOf(
                LessonSection(
                    title = "1. Anatomie d'une Classe et constructeur __init__",
                    content = """
Une classe est un plan de construction (moule), et un objet est une instance concrète créée à partir de ce plan.
• `__init__(self, ...)` est la méthode constructeur appelée automatiquement lors de la création d'un objet.
• `self` représente l'instance courante en cours de manipulation. Il doit être le premier paramètre de chaque méthode d'instance.
                    """.trimIndent(),
                    codeSnippet = """
class Guerrier:
    # Attribut de classe partagé par tous les guerriers
    royaume = "Valoria"

    def __init__(self, nom, points_de_vie, force):
        # Attributs d'instance spécifiques à cet objet
        self.nom = nom
        self.points_de_vie = points_de_vie
        self.force = force

    def attaquer(self, cible):
        degats = self.force
        cible.points_de_vie -= degats
        print(f"⚔️ {self.nom} inflige {degats} dégâts à {cible.nom} !")

# Instanciation
thor = Guerrier("Thor", 100, 25)
loki = Guerrier("Loki", 80, 15)

thor.attaquer(loki)
print(f"Vie restante de Loki : {loki.points_de_vie} PV")
                    """.trimIndent(),
                    expectedOutput = """
⚔️ Thor inflige 25 dégâts à Loki !
Vie restante de Loki : 55 PV
                    """.trimIndent(),
                    tip = "En Python, les noms de classes s'écrivent toujours en CamelCase (ex: CompteBancaire, GuerrierFeu)."
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_16_1",
                    question = "Que représente le paramètre obligatoire 'self' dans les méthodes de classe ?",
                    codeSnippet = null,
                    options = listOf(
                        "La classe parente",
                        "L'instance courante de l'objet",
                        "Un pointeur C vers la mémoire",
                        "Une variable globale"
                    ),
                    correctIndex = 1,
                    explanation = "self fait référence à l'instance spécifique sur laquelle la méthode est invoquée."
                ),
                QuizQuestion(
                    id = "q_16_2",
                    question = "Quel est le rôle de la méthode spéciale __init__ ?",
                    codeSnippet = null,
                    options = listOf(
                        "Détruire l'objet en mémoire",
                        "Initialiser les attributs de l'objet lors de sa création",
                        "Convertir l'objet en chaîne",
                        "Copier l'objet"
                    ),
                    correctIndex = 1,
                    explanation = "__init__ est le constructeur appelé automatiquement pour initialiser les attributs d'une nouvelle instance."
                )
            )
        ),

        // Leçon 17 : Dunder Methods
        PythonLesson(
            id = "py_17_dunder_methods",
            number = 17,
            title = "Méthodes Magiques (Dunder Methods)",
            subtitle = "__str__, __repr__, __len__, __eq__ et surcharge d'opérateurs",
            level = PythonLevel.AVANCE,
            durationMinutes = 11,
            xpReward = 110,
            summary = "Donnez des super-pouvoirs à vos objets en surchargeant les opérateurs de Python (+, ==, len, str).",
            sections = listOf(
                LessonSection(
                    title = "1. Les Dunder Methods (Double Underscore)",
                    content = """
Les méthodes « dunder » (ex: `__str__`, `__len__`) permettent à vos objets de s'intégrer nativement avec les fonctions et opérateurs de base de Python.
• `__str__(self)` : représentation lisible pour les utilisateurs (appelé par `print()` et `str()`).
• `__repr__(self)` : représentation technique pour les développeurs.
• `__len__(self)` : permet d'utiliser `len(mon_objet)`.
• `__add__(self, autre)` : surcharge l'opérateur `+`.
• `__eq__(self, autre)` : surcharge l'opérateur `==`.
                    """.trimIndent(),
                    codeSnippet = """
class Vecteur2D:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def __add__(self, autre):
        # Permet : v1 + v2
        return Vecteur2D(self.x + autre.x, self.y + autre.y)

    def __eq__(self, autre):
        # Permet : v1 == v2
        return self.x == autre.x and self.y == autre.y

    def __repr__(self):
        return f"Vecteur2D({self.x}, {self.y})"

v1 = Vecteur2D(2, 4)
v2 = Vecteur2D(3, 1)
v3 = v1 + v2 # Appelle automatiquement v1.__add__(v2)

print("Résultat addition :", v3)
print("v1 == v2 ?", v1 == v2)
                    """.trimIndent(),
                    expectedOutput = """
Résultat addition : Vecteur2D(5, 5)
v1 == v2 ? False
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_17_1",
                    question = "Quelle méthode magique est appelée quand vous faites print(mon_objet) ?",
                    codeSnippet = null,
                    options = listOf("__display__", "__str__", "__print__", "__text__"),
                    correctIndex = 1,
                    explanation = "__str__ renvoie la représentation textuelle 'humainement lisible' de l'objet, utilisée par print() et str()."
                ),
                QuizQuestion(
                    id = "q_17_2",
                    question = "Quelle méthode devez-vous implémenter pour surcharger l'opérateur d'addition '+' entre deux objets ?",
                    codeSnippet = null,
                    options = listOf("__sum__", "__plus__", "__add__", "__operator_add__"),
                    correctIndex = 2,
                    explanation = "__add__(self, other) est la méthode spéciale responsable du comportement de l'opérateur +."
                )
            )
        ),

        // Leçon 18 : Encapsulation & Propriétés
        PythonLesson(
            id = "py_18_properties",
            number = 18,
            title = "Encapsulation & Décorateur @property",
            subtitle = "Attributs protégés (_), privés (__), getters et setters pythoniques",
            level = PythonLevel.AVANCE,
            durationMinutes = 10,
            xpReward = 115,
            summary = "Contrôlez l'accès et la validation de vos données sans casser la syntaxe d'accès direct.",
            sections = listOf(
                LessonSection(
                    title = "1. Conventions Privées et Name Mangling",
                    content = """
En Python, il n'y a pas de mot-clé `private` strict : « Nous sommes tous des adultes consentants » (Guido van Rossum).
Conventions :
• `_attribut` (un tiret bas) : signale que l'attribut est interne et ne doit pas être modifié directement depuis l'extérieur.
• `__attribut` (deux tirets bas) : déclenche le **Name Mangling** (renommage automatique en `_NomClasse__attribut` pour éviter les collisions en héritage).
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Le Décorateur @property (Getter et Setter)",
                    content = """
Le décorateur `@property` permet d'accéder à une méthode comme s'il s'agissait d'un simple attribut, tout en exécutant de la logique de validation !
                    """.trimIndent(),
                    codeSnippet = """
class CompteBancaire:
    def __init__(self, titulaire, solde_initial):
        self.titulaire = titulaire
        self._solde = max(0, solde_initial)

    @property
    def solde(self):
        \"\"\"Getter : lecture sécurisée du solde.\"\"\"
        return self._solde

    @solde.setter
    def solde(self, nouveau_solde):
        \"\"\"Setter : validation avant modification.\"\"\"
        if nouveau_solde < 0:
            raise ValueError("Le solde ne peut pas être négatif !")
        self._solde = nouveau_solde

compte = CompteBancaire("Anos", 500)
print(f"Solde actuel : {compte.solde} €") # Accès direct élégant

compte.solde = 750 # Appelle le setter avec validation
print(f"Nouveau solde : {compte.solde} €")
                    """.trimIndent(),
                    expectedOutput = """
Solde actuel : 500 €
Nouveau solde : 750 €
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_18_1",
                    question = "Quel est le bénéfice principal du décorateur @property ?",
                    codeSnippet = null,
                    options = listOf(
                        "Il crypte la mémoire de l'objet",
                        "Il permet d'accéder à une méthode avec la syntaxe d'un attribut (sans parenthèses) et d'ajouter de la validation",
                        "Il rend la classe abstraite",
                        "Il convertit l'objet en JSON"
                    ),
                    correctIndex = 1,
                    explanation = "@property transforme une méthode en attribut calculé en lecture seule, et @nom.setter permet de valider toute modification."
                ),
                QuizQuestion(
                    id = "q_18_2",
                    question = "Que signifie le préfixe d'un seul tiret bas _attribut en Python ?",
                    codeSnippet = null,
                    options = listOf(
                        "C'est une erreur de syntaxe",
                        "Une convention indiquant que l'attribut est d'usage interne",
                        "L'attribut est supprimé",
                        "L'attribut est une constante absolue"
                    ),
                    correctIndex = 1,
                    explanation = "Un seul tiret bas est une convention forte signalant un membre privé/interne qui ne doit pas être altéré hors de la classe."
                )
            )
        ),

        // Leçon 19 : Héritage, Dataclasses & ABC
        PythonLesson(
            id = "py_19_inheritance_dataclasses",
            number = 19,
            title = "Héritage, Dataclasses & Classes Abstraites",
            subtitle = "super(), polymorphisme, module abc et décorateur @dataclass",
            level = PythonLevel.AVANCE,
            durationMinutes = 11,
            xpReward = 120,
            summary = "Structurez de grandes architectures orientées objet grâce à l'héritage, aux dataclasses et aux interfaces abstraites.",
            sections = listOf(
                LessonSection(
                    title = "1. Héritage et fonction super()",
                    content = """
L'héritage permet à une classe enfant de récupérer les attributs et méthodes d'une classe parente.
La fonction `super()` appelle le constructeur ou une méthode de la classe parente.
                    """.trimIndent(),
                    codeSnippet = """
class Animal:
    def __init__(self, nom):
        self.nom = nom

    def crier(self):
        return "Bruit générique"

class Chien(Animal):
    def __init__(self, nom, race):
        super().__init__(nom) # Appelle le constructeur de Animal
        self.race = race

    def crier(self):
        return "Ouaf Ouaf ! 🐶"

medor = Chien("Médor", "Golden Retriever")
print(f"{medor.nom} ({medor.race}) : {medor.crier()}")
                    """.trimIndent(),
                    expectedOutput = """
Médor (Golden Retriever) : Ouaf Ouaf ! 🐶
                    """.trimIndent()
                ),
                LessonSection(
                    title = "2. Les Dataclasses (PEP 557)",
                    content = """
Introduites en Python 3.7, les **Dataclasses** éliminent le code verbeux répétitif (boilerplate).
Le décorateur `@dataclass` génère automatiquement pour vous `__init__`, `__repr__`, `__eq__` et d'autres méthodes !
                    """.trimIndent(),
                    codeSnippet = """
from dataclasses import dataclass

@dataclass
class Livre:
    titre: String = "Sans titre"
    auteur: str = "Anonyme"
    prix: float = 0.0

livre1 = Livre("Apprendre Python", "Anos", 29.99)
livre2 = Livre("Apprendre Python", "Anos", 29.99)

print(livre1) # __repr__ généré automatiquement !
print("Identiques ?", livre1 == livre2) # __eq__ généré automatiquement !
                    """.trimIndent(),
                    expectedOutput = """
Livre(titre='Apprendre Python', auteur='Anos', prix=29.99)
Identiques ? True
                    """.trimIndent()
                )
            ),
            quiz = listOf(
                QuizQuestion(
                    id = "q_19_1",
                    question = "Quel est le rôle de super().__init__(...) dans une sous-classe ?",
                    codeSnippet = null,
                    options = listOf(
                        "Créer une seconde copie de l'objet",
                        "Invoquer le constructeur de la classe parente",
                        "Rendre la classe statique",
                        "Supprimer l'héritage"
                    ),
                    correctIndex = 1,
                    explanation = "super() permet d'appeler le constructeur de la classe parente pour initialiser ses attributs sans répéter de code."
                ),
                QuizQuestion(
                    id = "q_19_2",
                    question = "Quel module fournit le décorateur @dataclass en Python ?",
                    codeSnippet = null,
                    options = listOf("typing", "dataclasses", "collections", "functools"),
                    correctIndex = 1,
                    explanation = "Le module standard 'dataclasses' introduit dans Python 3.7 fournit le décorateur @dataclass."
                )
            )
        )
    )
}
