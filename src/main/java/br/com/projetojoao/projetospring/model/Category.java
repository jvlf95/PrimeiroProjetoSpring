package br.com.projetojoao.projetospring.model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String category;

    Category(String category){
        this.category = category;
    }

    public static Category fromString(String text){
        for(Category category : Category.values()){
            if(category.category.equalsIgnoreCase(text)){
                return category;
            }
        }
        throw new IllegalArgumentException("Category not found!");
    }


}
