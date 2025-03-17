package com.qlish.qlish_api.test.enums;

import com.qlish.qlish_api.question.enums.DifficultyLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Subject {
    ENGLISH(Map.of(
            DifficultyLevel.EASY, Set.of("antonyms", "reading comprehension", "sentence completion", "synonyms"),
            DifficultyLevel.MEDIUM, Set.of("parts of speech", "tenses", "oral sounds", "literary devices", "vocabulary"),
            DifficultyLevel.HARD, Set.of("spelling", "essay writing", "poetry analysis", "literary devices")
    )),
    MATH(Map.of(
            DifficultyLevel.EASY, Set.of("arithmetic", "fractions", "word problems", "measurement"),
            DifficultyLevel.MEDIUM, Set.of("algebra", "geometry", "linear equations", "word problems", "trigonometry"),
            DifficultyLevel.HARD, Set.of("calculus", "statistics", "probability", "trigonometry", "number theory")
    )),
    PHYSICS(Map.of(
            DifficultyLevel.EASY, Set.of("mechanics", "energy", "waves"),
            DifficultyLevel.MEDIUM, Set.of("thermodynamics", "electromagnetism", "optics", "kinematics", "magnetism"),
            DifficultyLevel.HARD, Set.of("modern physics", "nuclear physics", "astrophysics", "scientific laws")
    )),
    CHEMISTRY(Map.of(
            DifficultyLevel.EASY, Set.of("periodic table", "chemical bonding", "states of matter"),
            DifficultyLevel.MEDIUM, Set.of("chemical reactions", "acids & bases", "stoichiometry", "lab techniques"),
            DifficultyLevel.HARD, Set.of("organic chemistry", "biochemistry", "thermodynamics", "electrochemistry", "nuclear chemistry")
    )),
    BIOLOGY(Map.of(
            DifficultyLevel.EASY, Set.of("cell biology", "human anatomy", "plant biology"),
            DifficultyLevel.MEDIUM, Set.of("genetics", "evolution", "ecology", "zoology"),
            DifficultyLevel.HARD, Set.of("microbiology", "marine biology", "biotechnology", "immunology", "DNA structure")
    )),
    HISTORY(Map.of(
            DifficultyLevel.EASY, Set.of("ancient civilizations", "medieval history", "world wars"),
            DifficultyLevel.MEDIUM, Set.of("modern history", "revolutions", "historical figures", "cultural history"),
            DifficultyLevel.HARD, Set.of("north american & australian history", "european history", "asian history", "african history", "middle eastern history", "south american history")
    )),
    ACCOUNTING(Map.of(
            DifficultyLevel.EASY, Set.of("financial statements", "bookkeeping", "taxation"),
            DifficultyLevel.MEDIUM, Set.of("auditing", "managerial accounting", "cost accounting", "budgeting"),
            DifficultyLevel.HARD, Set.of("payroll management", "ethics", "international standards", "forensic accounting")
    )),
    SOCIOLOGY(Map.of(
            DifficultyLevel.EASY, Set.of("family structures", "education systems", "cultural norms"),
            DifficultyLevel.MEDIUM, Set.of("social theory", "religion", "crime & deviance", "gender studies", "urbanization"),
            DifficultyLevel.HARD, Set.of("race & ethnicity", "social movements", "social stratification")
    )),
    POLITICAL_SCIENCE(Map.of(
            DifficultyLevel.EASY, Set.of("government systems", "political theory", "elections"),
            DifficultyLevel.MEDIUM, Set.of("international relations", "public policy", "comparative politics", "political ideologies"),
            DifficultyLevel.HARD, Set.of("constitutional law", "diplomacy", "human rights", "political economy")
    )),
    SPORTS(Map.of(
            DifficultyLevel.EASY, Set.of("olympics", "sports history", "sport rules & regulations"),
            DifficultyLevel.MEDIUM, Set.of("famous athletes", "sports leagues", "american football", "basketball", "cricket", "association football", "baseball", "tennis", "boxing", "formula 1"),
            DifficultyLevel.HARD, Set.of("association football", "baseball", "tennis", "boxing", "formula 1", "famous athletes", "sports leagues", "american football", "basketball", "cricket")
    )),
    MEDIA(Map.of(
            DifficultyLevel.EASY, Set.of("journalism", "film & tv", "social media"),
            DifficultyLevel.MEDIUM, Set.of("advertising", "mass communication", "media ethics", "digital media"),
            DifficultyLevel.HARD, Set.of("public relations", "broadcasting", "media laws")
    )),
    GEOGRAPHY(Map.of(
            DifficultyLevel.EASY, Set.of("physical geography", "human geography", "countries & capitals"),
            DifficultyLevel.MEDIUM, Set.of("cartography", "GIS", "climate patterns", "environmental issues", "cultural geography"),
            DifficultyLevel.HARD, Set.of("economic geography", "political geography", "natural resources", "geopolitics")
    )),
    COMPUTER_SCIENCE(Map.of(
            DifficultyLevel.EASY, Set.of("programming", "databases", "web development"),
            DifficultyLevel.MEDIUM, Set.of("algorithms", "data structures", "cybersecurity", "operating systems"),
            DifficultyLevel.HARD, Set.of("artificial intelligence", "computer networks", "software engineering", "algorithms")
    )),
    ART_LITERATURE(Map.of(
            DifficultyLevel.EASY, Set.of("art history", "famous artists", "literary genres"),
            DifficultyLevel.MEDIUM, Set.of("classic literature", "poetry", "literary devices", "dance forms"),
            DifficultyLevel.HARD, Set.of("music history", "architecture", "art movements", "literary devices")
    )),
    GENERAL_KNOWLEDGE(Map.of(
            DifficultyLevel.EASY, Set.of("current affairs", "countries & capitals"),
            DifficultyLevel.MEDIUM, Set.of("celebrities", "pop culture & entertainment", "countries & capitals", "historical events & dates"),
            DifficultyLevel.HARD, Set.of("pop culture & entertainment", "current affairs", "historical events & dates")
    ));


    private final Map<DifficultyLevel, Set<String>> topics;

    public static Subject fromName(String name) {
            return Arrays.stream(Subject.values())
                .filter(subject -> subject.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid subject: " + name));

    }

    public Set<String> getTopicsForLevel(DifficultyLevel level) {
        return topics.get(level);
    }

}
