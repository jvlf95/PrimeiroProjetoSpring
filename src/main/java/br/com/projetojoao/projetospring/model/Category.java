package br.com.projetojoao.projetospring.model;

public enum Category {
    ACTION("Action"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    THRILLER("Thriller"),
    DRAMA("Drama");


    private String omdbCategory;

    Category(String omdbCategory){
        this.omdbCategory = omdbCategory;
    }

    public static Category fromString(String str){
        for(Category category : Category.values()){
            if(category.omdbCategory.equalsIgnoreCase(str)){
                return category;
            }
        }

        throw new IllegalArgumentException("Nothing found for " + str);
    }
}
