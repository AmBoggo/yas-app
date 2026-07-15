"""Lista curada de 100 palavras em inglês para o YAS.

Cada palavra tem: texto, definição, fonética (opcional).
Usado pelo service.py para a Palavra do Dia.
"""

from dataclasses import dataclass
from datetime import date


@dataclass
class Palavra:
    texto: str
    definicao: str
    exemplo: str
    fonetica: str = ""


# Palavras selecionadas por serem curiosas, bonitas ou úteis
# Nível: intermediário a avançado
LISTA: list[Palavra] = [
    # ── Curiosas ──
    Palavra("Serendipity", "The occurrence of events by chance in a happy way",
            "Finding that bookshop was pure serendipity.", "/ˌserənˈdɪpɪti/"),
    Palavra("Petrichor", "The smell of earth after rain",
            "I love the petrichor after a summer storm.", "/ˈpɛtrɪkɔːr/"),
    Palavra("Ephemeral", "Lasting for a very short time",
            "The beauty of cherry blossoms is ephemeral.", "/ɪˈfemərəl/"),
    Palavra("Defenestration", "The act of throwing someone out of a window",
            "The defenestration was surprisingly peaceful.", "/diːˌfenɪˈstreɪʃən/"),
    Palavra("Ethereal", "Extremely delicate and light in a way that seems heavenly",
            "The music had an ethereal quality.", "/ɪˈθɪəriəl/"),
    # ── Sentimentos ──
    Palavra("Nostalgia", "A sentimental longing for the past",
            "She felt nostalgia for her childhood summers.", "/nɒˈstældʒə/"),
    Palavra("Melancholy", "A deep, thoughtful sadness",
            "The rainy day filled him with melancholy.", "/ˈmelənkɒli/"),
    Palavra("Euphoria", "An intense feeling of happiness",
            "Winning the championship gave her pure euphoria.", "/juːˈfɔːriə/"),
    Palavra("Reverie", "A state of dreamy meditation",
            "She was lost in reverie, staring out the window.", "/ˈrevəri/"),
    Palavra("Zeal", "Great energy and enthusiasm for a cause",
            "He approached the project with religious zeal.", "/ziːl/"),
    # ── Pensamento ──
    Palavra("Ambiguous", "Open to more than one interpretation",
            "His answer was deliberately ambiguous.", "/æmˈbɪɡjuəs/"),
    Palavra("Eloquent", "Fluent and persuasive in speaking",
            "She gave an eloquent speech at the ceremony.", "/ˈeləkwənt/"),
    Palavra("Resilient", "Able to recover quickly from difficulties",
            "Children are remarkably resilient.", "/rɪˈzɪliənt/"),
    Palavra("Tenacious", "Tending to keep a firm hold of something",
            "She was tenacious in her pursuit of justice.", "/tɪˈneɪʃəs/"),
    Palavra("Paradigm", "A typical example or pattern of something",
            "This discovery represents a paradigm shift.", "/ˈpærədaɪm/"),
    # ── Natureza ──
    Palavra("Cascade", "A small waterfall or series of stages",
            "Water cascaded down the rocks.", "/kæˈskeɪd/"),
    Palavra("Luminous", "Full of or shedding light",
            "The stars were luminous in the dark sky.", "/ˈluːmɪnəs/"),
    Palavra("Verdant", "Green with grass or rich vegetation",
            "The valley was lush and verdant.", "/ˈvɜːrdənt/"),
    Palavra("Zephyr", "A soft, gentle breeze",
            "A warm zephyr blew through the garden.", "/ˈzefər/"),
    Palavra("Ebullient", "Cheerful and full of energy",
            "She was ebullient at her graduation.", "/ɪˈbʌliənt/"),
    # ── Incomuns ──
    Palavra("Juxtaposition", "Placing two things together for comparison",
            "The juxtaposition of old and new was striking.", "/ˌdʒʌkstəpəˈzɪʃən/"),
    Palavra("Anachronism", "Something out of its proper time period",
            "A smartphone in a medieval painting is an anachronism.", "/əˈnækrənɪzəm/"),
    Palavra("Dichotomy", "A division into two opposite groups",
            "There's a dichotomy between theory and practice.", "/daɪˈkɒtəmi/"),
    Palavra("Precarious", "Not securely held in position",
            "The climber was in a precarious position.", "/prɪˈkeəriəs/"),
    Palavra("Ominous", "Giving the impression something bad will happen",
            "Dark clouds gathered, looking ominous.", "/ˈɒmɪnəs/"),
    # ── Bonitas ──
    Palavra("Whimsical", "Playfully quaint or fanciful",
            "The garden had a whimsical charm.", "/ˈwɪmzɪkəl/"),
    Palavra("Incandescent", "Emitting light as a result of being heated",
            "Her incandescent smile lit up the room.", "/ˌɪnkænˈdesənt/"),
    Palavra("Mellifluous", "Sweet or musical; pleasant to hear",
            "Her mellifluous voice calmed everyone.", "/məˈlɪfluəs/"),
    Palavra("Cacophony", "A harsh mixture of sounds",
            "The city street was a cacophony of noise.", "/kəˈkɒfəni/"),
    Palavra("Opulent", "Rich and luxurious",
            "The palace was decorated in opulent style.", "/ˈɒpjʊlənt/"),
    # ── Movimento ──
    Palavra("Transient", "Lasting only for a short time",
            "The beauty of the sunset was transient.", "/ˈtrænziənt/"),
    Palavra("Perpetual", "Never ending or changing",
            "The city is in a state of perpetual motion.", "/pərˈpetʃuəl/"),
    Palavra("Voracious", "Wanting or devouring great amounts",
            "She was a voracious reader.", "/vəˈreɪʃəs/"),
    Palavra("Inevitable", "Certain to happen; unavoidable",
            "Change is the only inevitable thing.", "/ɪnˈevɪtəbəl/"),
    Palavra("Prolific", "Producing much fruit or many works",
            "He was a prolific writer of short stories.", "/prəˈlɪfɪk/"),
    # ── Precisão ──
    Palavra("Ambivalent", "Having mixed feelings about something",
            "She felt ambivalent about moving abroad.", "/æmˈbɪvələnt/"),
    Palavra("Articulate", "Having or showing the ability to speak fluently",
            "She is remarkably articulate for her age.", "/ɑːrˈtɪkjʊlət/"),
    Palavra("Concise", "Giving information clearly in few words",
            "Her writing is concise and powerful.", "/kənˈsaɪs/"),
    Palavra("Plausible", "Seeming reasonable or probable",
            "His explanation sounded plausible.", "/ˈplɔːzəbəl/"),
    Palavra("Prudent", "Acting with care and thought for the future",
            "It's prudent to save money for emergencies.", "/ˈpruːdənt/"),
    # ── Extras ──
    Palavra("Surreptitious", "Kept secret because it would not be approved",
            "He took a surreptitious glance at his phone.", "/ˌsʌrəpˈtɪʃəs/"),
    Palavra("Benevolent", "Well-meaning and kindly",
            "The benevolent teacher helped everyone.", "/bəˈnevələnt/"),
    Palavra("Malevolent", "Having a wish to do evil to others",
            "The villain had a malevolent laugh.", "/məˈlevələnt/"),
    Palavra("Candid", "Truthful and straightforward",
            "She gave a candid interview about her struggles.", "/ˈkændɪd/"),
    Palavra("Magnanimous", "Very generous or forgiving",
            "He was magnanimous in victory.", "/mæɡˈnænɪməs/"),
    # ── Palavras 46-50 ──
    Palavra("Halcyon", "A period of time in the past that was happy and peaceful",
            "She recalled the halcyon days of her childhood.", "/ˈhælsiən/"),
    Palavra("Sonder", "The realization that every person has a life as complex as your own",
            "Walking through the crowd, she felt a deep sense of sonder.", ""),
    Palavra("Liminal", "Relating to a transitional stage",
            "The liminal space between sleep and waking is fascinating.", "/ˈlɪmɪnəl/"),
    Palavra("Ubiquitous", "Present everywhere at once",
            "Smartphones have become ubiquitous.", "/juːˈbɪkwɪtəs/"),
    Palavra("Nemesis", "A long-standing rival or archenemy",
            "He finally faced his nemesis.", "/ˈneməsɪs/"),
    # ── Palavras 51-55 ──
    Palavra("Awe", "A feeling of reverential respect mixed with fear",
            "She stood in awe of the vast canyon.", "/ɔː/"),
    Palavra("Angst", "A feeling of deep anxiety about the world",
            "Teenage angst is a common theme in literature.", "/æŋst/"),
    Palavra("Ennui", "A feeling of listlessness and boredom",
            "The rainy season brought a sense of ennui.", "/ɒnˈwiː/"),
    Palavra("Aplomb", "Self-confidence under pressure",
            "She handled the crisis with aplomb.", "/əˈplɒm/"),
    Palavra("Trepidation", "A feeling of fear about something that may happen",
            "He approached the meeting with trepidation.", "/ˌtrepɪˈdeɪʃən/"),
    # ── Palavras 56-60 ──
    Palavra("Paradox", "A seemingly contradictory statement that may be true",
            "This is a paradox: the more you give, the more you have.", "/ˈpærədɒks/"),
    Palavra("Perspicacious", "Having a ready insight into things",
            "Her perspicacious observation solved the mystery.", "/ˌpɜːrspɪˈkeɪʃəs/"),
    Palavra("Meticulous", "Showing great attention to detail",
            "The artist was meticulous in her work.", "/mɪˈtɪkjʊləs/"),
    Palavra("Enigma", "A person or thing that is mysterious",
            "The ancient manuscript remains an enigma.", "/ɪˈnɪɡmə/"),
    Palavra("Quintessential", "Representing the most perfect example",
            "Paris is the quintessential romantic city.", "/ˌkwɪntɪˈsenʃəl/"),
    # ── Palavras 61-65 ──
    Palavra("Resplendent", "Impressive and bright",
            "The garden was resplendent with flowers.", "/rɪˈsplendənt/"),
    Palavra("Prismatic", "Relating to a prism; many-colored",
            "The light created a prismatic effect.", "/prɪzˈmætɪk/"),
    Palavra("Viridescent", "Becoming green; greenish",
            "The hills were viridescent after the rain.", "/ˌvɪrɪˈdesənt/"),
    Palavra("Aurora", "The dawn; a natural light display",
            "We stayed up to see the aurora borealis.", "/ɔːˈrɔːrə/"),
    Palavra("Ripple", "A small wave or series of waves",
            "He threw a stone and watched the ripples spread.", "/ˈrɪpəl/"),
    # ── Palavras 66-70 ──
    Palavra("Antithesis", "A person or thing that is the direct opposite",
            "Love is the antithesis of hate.", "/ænˈtɪθəsɪs/"),
    Palavra("Epitome", "A person or thing that is a perfect example",
            "She was the epitome of elegance.", "/ɪˈpɪtəmi/"),
    Palavra("Frivolous", "Not having any serious purpose",
            "The frivolous spending worried her parents.", "/ˈfrɪvələs/"),
    Palavra("Tangible", "Clear and definite; real",
            "We need tangible evidence to proceed.", "/ˈtændʒəbəl/"),
    Palavra("Tenuous", "Very weak or slight",
            "The connection between the events was tenuous.", "/ˈtenjuəs/"),
    # ── Palavras 71-75 ──
    Palavra("Breathtaking", "Astonishing or awe-inspiring",
            "The view from the mountaintop was breathtaking.", "/ˈbreθteɪkɪŋ/"),
    Palavra("Radiant", "Sending out light or joy",
            "The bride looked radiant on her wedding day.", "/ˈreɪdiənt/"),
    Palavra("Sonorous", "Deep and full in sound",
            "The sonorous bell echoed through the valley.", "/ˈsɒnərəs/"),
    Palavra("Lissome", "Thin, supple, and graceful",
            "The dancer was lissome and elegant.", "/ˈlɪsəm/"),
    Palavra("Relentless", "Unyielding; never stopping",
            "The relentless rain flooded the streets.", "/rɪˈlentləs/"),
    # ── Palavras 76-80 ──
    Palavra("Imminent", "About to happen",
            "With the storm imminent, they took shelter.", "/ˈɪmɪnənt/"),
    Palavra("Spontaneous", "Performed without premeditation",
            "They broke into spontaneous applause.", "/spɒnˈteɪniəs/"),
    Palavra("Impulsive", "Acting without forethought",
            "His impulsive decision surprised everyone.", "/ɪmˈpʌlsɪv/"),
    Palavra("Diligent", "Having careful and persistent effort",
            "The diligent student finished early.", "/ˈdɪlɪdʒənt/"),
    Palavra("Apathetic", "Showing no interest or emotion",
            "The audience was apathetic during the speech.", "/ˌæpəˈθetɪk/"),
    # ── Palavras 81-85 ──
    Palavra("Coherent", "Logical and consistent",
            "She gave a coherent explanation of the theory.", "/kəʊˈhɪərənt/"),
    Palavra("Feasible", "Possible to do easily",
            "Is it feasible to finish by Friday?", "/ˈfiːzəbəl/"),
    Palavra("Viable", "Capable of working successfully",
            "The business plan is financially viable.", "/ˈvaɪəbəl/"),
    Palavra("Tactful", "Having a sense of what is appropriate",
            "He was tactful in addressing the sensitive issue.", "/ˈtæktfəl/"),
    Palavra("Superfluous", "Unnecessary; more than needed",
            "The extra decorations seemed superfluous.", "/suːˈpɜːfluəs/"),
    # ── Palavras 86-90 ──
    Palavra("Obfuscate", "To make unclear or difficult to understand",
            "Politicians often obfuscate their true intentions.", "/ˈɒbfʌskeɪt/"),
    Palavra("Pragmatic", "Dealing with things in a practical way",
            "We need a pragmatic approach to the problem.", "/præɡˈmætɪk/"),
    Palavra("Dogmatic", "Inclined to assert opinions as facts",
            "His dogmatic views left no room for discussion.", "/dɒɡˈmætɪk/"),
    Palavra("Debacle", "A complete failure or collapse",
            "The project was a debacle from start to finish.", "/deɪˈbɑːkəl/"),
    Palavra("Fiasco", "A thing that is a complete failure",
            "The outdoor wedding was a fiasco in the rain.", "/fiˈæskəʊ/"),
    # ── Palavras 91-95 ──
    Palavra("Callous", "Showing no concern for others",
            "His callous remarks hurt everyone.", "/ˈkæləs/"),
    Palavra("Compassionate", "Feeling sympathy for others",
            "The compassionate nurse comforted the patient.", "/kəmˈpæʃənət/"),
    Palavra("Empathetic", "Able to understand others' feelings",
            "An empathetic listener is hard to find.", "/ˌempəˈθetɪk/"),
    Palavra("Nefarious", "Wicked or criminal",
            "The villain had a nefarious plan.", "/nɪˈfeəriəs/"),
    Palavra("Vindictive", "Seeking revenge",
            "She was not vindictive despite the betrayal.", "/vɪnˈdɪktɪv/"),
    # ── Palavras 96-100 ──
    Palavra("Docile", "Ready to accept control or instruction",
            "The docile puppy followed every command.", "/ˈdəʊsaɪl/"),
    Palavra("Stoic", "Enduring pain without complaint",
            "He remained stoic throughout the ordeal.", "/ˈstəʊɪk/"),
    Palavra("Blunt", "Direct and outspoken",
            "Her blunt honesty was refreshing.", "/blʌnt/"),
    Palavra("Whimsy", "Playfully unusual behavior",
            "The garden was full of whimsy and charm.", "/ˈwɪmzi/"),
    Palavra("Stellar", "Exceptionally good; outstanding",
            "The team gave a stellar performance.", "/ˈstelər/"),
]


def palavra_do_dia(hoje: date | None = None) -> Palavra:
    """Retorna a palavra do dia baseada na data.

    Usa o dia do ano como índice para rotacionar a lista.
    """
    if hoje is None:
        hoje = date.today()

    indice = hoje.timetuple().tm_yday % len(LISTA)
    return LISTA[indice]