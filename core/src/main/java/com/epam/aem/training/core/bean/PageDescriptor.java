package com.epam.aem.training.core.bean;

public class PageDescriptor {

    private String title;
    private String text;
    private String pathToImage;
    private String referenceToPage;

    public PageDescriptor() {
    }

    public PageDescriptor(String title, String text, String pathToImage, String referenceToPage) {
        this.title = title;
        this.text = text;
        this.pathToImage = pathToImage;
        this.referenceToPage = referenceToPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getReferenceToPage() {
        return referenceToPage;
    }

    public void setReferenceToPage(String referenceToPage) {
        this.referenceToPage = referenceToPage;
    }

    @Override
    public String toString() {
        return "Page Descriptor{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", pathToImage='" + pathToImage + '\'' +
                ", referenceToPage='" + referenceToPage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageDescriptor pageDescriptor = (PageDescriptor) o;

        return referenceToPage != null ? referenceToPage.equals(pageDescriptor.referenceToPage) : pageDescriptor.referenceToPage == null;
    }

    @Override
    public int hashCode() {
        return referenceToPage != null ? referenceToPage.hashCode() : 0;
    }
}
