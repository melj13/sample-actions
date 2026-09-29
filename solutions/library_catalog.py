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
        print(f"Title: {self.title}")
        print(f"Author: {self.author}")
        print(f"Year: {self.year}")


class Book(Item):

    def __init__(self, title, author, year, genre, isbn):
        super().__init__(title, author, year)
        self.genre = genre
        self.isbn = isbn

    def display_info(self):
        super().display_info()
        print(f"Genre: {self.genre}")
        print(f"ISBN: {self.isbn}")


class DVD(Item):

    def __init__(self, title, author, year, duration):
        super().__init__(title, author, year)
        self.duration = duration

    def display_info(self):
        super().display_info()
        print(f"Duration: {self.duration} minutes")
