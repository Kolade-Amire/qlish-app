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
            DifficultyLevel.ELEMENTARY, Set.of("antonyms", "reading comprehension", "sentence completion", "synonyms"),
            DifficultyLevel.INTERMEDIATE, Set.of("parts of speech", "tenses", "oral sounds", "literary devices", "vocabulary"),
            DifficultyLevel.ADVANCED, Set.of("spelling", "essay writing", "poetry analysis", "literary devices")
    )),
    MATH(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("arithmetic", "fractions", "word problems", "measurement"),
            DifficultyLevel.INTERMEDIATE, Set.of("algebra", "geometry", "linear equations", "word problems", "trigonometry"),
            DifficultyLevel.ADVANCED, Set.of("calculus", "statistics", "probability", "trigonometry", "number theory")
    )),
    PHYSICS(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("mechanics", "energy", "waves"),
            DifficultyLevel.INTERMEDIATE, Set.of("thermodynamics", "electromagnetism", "optics", "kinematics", "magnetism"),
            DifficultyLevel.ADVANCED, Set.of("modern physics", "nuclear physics", "astrophysics", "scientific laws")
    )),
    CHEMISTRY(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("periodic table", "chemical bonding", "states of matter"),
            DifficultyLevel.INTERMEDIATE, Set.of("chemical reactions", "acids & bases", "stoichiometry", "lab techniques"),
            DifficultyLevel.ADVANCED, Set.of("organic chemistry", "biochemistry", "thermodynamics", "electrochemistry", "nuclear chemistry")
    )),
    BIOLOGY(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("cell biology", "human anatomy", "plant biology"),
            DifficultyLevel.INTERMEDIATE, Set.of("genetics", "evolution", "ecology", "zoology"),
            DifficultyLevel.ADVANCED, Set.of("microbiology", "marine biology", "biotechnology", "immunology", "DNA structure")
    )),
    HISTORY(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("ancient civilizations", "medieval history", "world wars"),
            DifficultyLevel.INTERMEDIATE, Set.of("modern history", "revolutions", "historical figures", "cultural history"),
            DifficultyLevel.ADVANCED, Set.of("north american & australian history", "european history", "asian history", "african history", "middle eastern history", "south american history")
    )),
    ACCOUNTING(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("financial statements", "bookkeeping", "taxation"),
            DifficultyLevel.INTERMEDIATE, Set.of("auditing", "managerial accounting", "cost accounting", "budgeting"),
            DifficultyLevel.ADVANCED, Set.of("payroll management", "ethics", "international standards", "forensic accounting")
    )),
    SOCIOLOGY(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("family structures", "education systems", "cultural norms"),
            DifficultyLevel.INTERMEDIATE, Set.of("social theory", "religion", "crime & deviance", "gender studies", "urbanization"),
            DifficultyLevel.ADVANCED, Set.of("race & ethnicity", "social movements", "social stratification")
    )),
    POLITICAL_SCIENCE(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("government systems", "political theory", "elections"),
            DifficultyLevel.INTERMEDIATE, Set.of("international relations", "public policy", "comparative politics", "political ideologies"),
            DifficultyLevel.ADVANCED, Set.of("constitutional law", "diplomacy", "human rights", "political economy")
    )),
    SPORTS(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("olympics", "sports history", "sport rules & regulations"),
            DifficultyLevel.INTERMEDIATE, Set.of("famous athletes", "sports leagues", "american football", "basketball", "cricket", "association football", "baseball", "tennis", "boxing", "formula 1"),
            DifficultyLevel.ADVANCED, Set.of("association football", "baseball", "tennis", "boxing", "formula 1", "famous athletes", "sports leagues", "american football", "basketball", "cricket")
    )),
    MEDIA(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("journalism", "film & tv", "social media"),
            DifficultyLevel.INTERMEDIATE, Set.of("advertising", "mass communication", "media ethics", "digital media"),
            DifficultyLevel.ADVANCED, Set.of("public relations", "broadcasting", "media laws")
    )),
    GEOGRAPHY(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("physical geography", "human geography", "countries & capitals"),
            DifficultyLevel.INTERMEDIATE, Set.of("cartography", "GIS", "climate patterns", "environmental issues", "cultural geography"),
            DifficultyLevel.ADVANCED, Set.of("economic geography", "political geography", "natural resources", "geopolitics")
    )),
    COMPUTER_SCIENCE(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("programming", "databases", "web development"),
            DifficultyLevel.INTERMEDIATE, Set.of("algorithms", "data structures", "cybersecurity", "operating systems"),
            DifficultyLevel.ADVANCED, Set.of("artificial intelligence", "computer networks", "software engineering", "algorithms")
    )),
    ART_LITERATURE(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("art history", "famous artists", "literary genres"),
            DifficultyLevel.INTERMEDIATE, Set.of("classic literature", "poetry", "literary devices", "dance forms"),
            DifficultyLevel.ADVANCED, Set.of("music history", "architecture", "art movements", "literary devices")
    )),
    GENERAL_KNOWLEDGE(Map.of(
            DifficultyLevel.ELEMENTARY, Set.of("current affairs", "countries & capitals"),
            DifficultyLevel.INTERMEDIATE, Set.of("celebrities", "pop culture and entertainment", "countries & capitals", "historical events and dates"),
            DifficultyLevel.ADVANCED, Set.of("pop culture and entertainment", "current affairs", "historical events and dates")
    ));


    private final Map<DifficultyLevel, Set<String>> topicsByLevel;

    public static Subject fromName(String name) {
        return Arrays.stream(Subject.values())
                .filter(subject -> subject.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid subject: " + name));

    }

    public Set<String> getTopicsForLevel(DifficultyLevel level) {
        return topicsByLevel.get(level);
    }

}
