import os


class Item:

    def __init__(self, title, author, year):
        self.title = title
        self.author = author
        self.year = year

    def __str__(self):
        return f"{self.title} by {self.author} ({self.year})"

    def __repr__(self):
        return f"Item: {self.title} by {self.author} ({self.year})"

    def display_info(self):
        return (
            f"Title: {self.title}\n"
            f"Author: {self.author}\n"
            f"Year: {self.year}\n"
        )


class Book(Item):

    def __init__(self, title, author, year, genre, ISBN):
        super().__init__(title, author, year)
        self.genre = genre
        self.ISBN = ISBN
        self.isbn = ISBN

    def display_info(self):
        return (
            super().display_info()
            + f"Genre: {self.genre}\n"
            + f"ISBN: {self.ISBN}\n"
        )


class DVD(Item):

    def __init__(self, title, author, year, duration):
        super().__init__(title, author, year)
        self.duration = duration

    def display_info(self):
        return super().display_info() + f"Duration: {self.duration} minutes\n"
