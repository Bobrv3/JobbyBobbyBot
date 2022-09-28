package org.bobrov.JobbyBobby.model.criteria;

public interface SearchCriteria {
    class Text implements SearchCriteria{
        private final String title = "Искомый текст";
        private String value;

        public Text(String value) {
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

    enum Experience implements SearchCriteria{
        noExperience("нет опыта"),
        between1And3("От 1 года до 3 лет"),
        between3And6("От 3 до 6 лет"),
        moreThan6("Более 6 лет");

        private final String title = "Опыт";
        private final String criteriaName;

        Experience(String criteriaName) {
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

    enum Search_field implements SearchCriteria{
        name("в названии вакансии"),
        company_name("в названии компании"),
        description("в описании вакансии");

        public final String title = "Искать в";
        public final String criteriaName;

        Search_field(String criteriaName) {
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

    enum Area implements SearchCriteria{
        Belarus(16, "Беларусь"),
        Russia(113, "Россия");

        public final String title = "Страна";
        public final String criteriaName;
        public final int id;

        Area(int id, String criteriaName) {
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
