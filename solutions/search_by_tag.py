import json


class SearchByTag:

    def __init__(self, data_file, query_tag):
        with open(data_file) as data_file:
            self._data = json.load(data_file)
        self.query = query_tag

    def search(self):
        # Generator: yields every item whose tags contain the query tag.
        # The file may be empty and an item may carry no "tags" key at all.
        # Empty tuples are used as defaults so no list object is built here.
        for item in self._data.get('items') or ():
            if self.query in (item.get('tags') or ()):
                yield item

    def first(self):
        # next() on the generator raises StopIteration when nothing matches,
        # which is exactly the required behaviour.
        return next(self.search())
