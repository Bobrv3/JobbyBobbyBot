package org.bobrov.JobbyBobby.model.criteria;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface SearchCriteria {
    class TEXT implements SearchCriteria, Serializable {
        private final String title = "Искать";
        private String value;

        public TEXT() {
            value = "java";
        }

        public TEXT(String value) {
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    class PERIOD implements SearchCriteria, Serializable {
        private final String title = "Период";
        private int value;

        public PERIOD() {
            value = 1;
        }

        public PERIOD(int value) {
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return String.valueOf(value);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    class DATE_FROM implements SearchCriteria, Serializable {
        private final String title = "Дата с";
        private LocalDateTime value;

        public DATE_FROM() {
            value = LocalDateTime.now().minusDays(1);
        }

        public DATE_FROM(LocalDateTime value) {
            this.value = value;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return value.toString();
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    enum EXPERIENCE implements SearchCriteria, Serializable {
        noExperience("нет опыта"),
        between1And3("От 1 года до 3 лет"),
        between3And6("От 3 до 6 лет"),
        moreThan6("Более 6 лет");

        private final String title = "Опыт";
        private final String criteriaName;

        EXPERIENCE(String criteriaName) {
            this.criteriaName = criteriaName;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return criteriaName;
        }
    }

    enum SEARCH_FIELD implements SearchCriteria, Serializable {
        name("в названии вакансии"),
        company_name("в названии компании"),
        description("в описании вакансии");

        public final String title = "Искать в";
        public final String criteriaName;

        SEARCH_FIELD(String criteriaName) {
            this.criteriaName = criteriaName;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return criteriaName;
        }
    }

    enum AREA implements SearchCriteria, Serializable {
        BELARUS(16, "Беларусь"),
        RUSSIA(113, "Россия");

        public final String title = "Страна";
        public final String criteriaName;
        public final int id;

        AREA(int id, String criteriaName) {
            this.criteriaName = criteriaName;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getCriteriaName() {
            return criteriaName;
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }

    String getTitle();
    String getCriteriaName();
}
