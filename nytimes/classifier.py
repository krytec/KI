#!/usr/bin/env python
# -*- coding: iso-8859-1 -*-

from preprocess_documents import read_and_preprocess_documents
import os
import sys
import math
import argparse
import pickle

"""

  Exercise "Document Classification" 
  -------------------------
  a small interface for document classification.
  Fill in code (see FIXMEs) and implement your own 
  document classifier.

  Train the classifier on the training documents like this:

  > python classifier.py --train data/train/*/*

  Apply the classifier to the test documents like this:

  > python classifier.py --apply data/test/*/*

"""


class DocumentClassifier:

    def __init__(self):
        """ The classifier should store all its learned information
            in this 'model' object. Pick whatever form seems appropriate
            to you. Recommendation: use 'pickle' to store/load objects! """
        self.model = None

    def save(self, file):
        pickle.dump(self.model, open(str(file)+'.p', 'wb'))

    def load(self, file):
        self.model = pickle.load(open(str(file)+'.p', 'rb'))

    def train(self, features, labels, wordmap):
        """
        trains a document classifier and stores all relevant
        information in 'self.model'.

        @type features: dict
        @param features: Each entry in 'features' represents a document
                         by its (sparse) bag-of-words vector. 'features'
                         is of the following form (i.e., for each document,
                         all terms occurring in the document and their
                         counts are stored in a dictionary):
                         {
                           'doc1.txt':
                              {
                                'the' : 17,
                                'world': 3,
                                ...
                              },
                           'doc2.txt':
                              {
                                'community' : 2,
                                'college': 1,
                                ...
                              },
                            ...
                         }
        @type labels: dict
        @param labels: 'labels' contains the class labels for all documents
                       in dictionary form:
                       {
                           'doc1.txt': 'arts',
                           'doc2.txt': 'business',
                           'doc3.txt': 'sports',
                           ...
                       }
        """
        # words= {}
        # count = 0
        # for k,v in features.items():
        #     for word in v.values():
        #         count += word
        #     words[k]=count

        # dictionary that contains probability of each category P(C)
        category_probability = {}
        for k, v in labels.items():
            if v not in category_probability.keys():
                category_probability[v] = len([x for x in labels.keys() if labels[x] == v]) / len(labels)

        print('\n=== P(Kategorie) ===')
        for k, v in category_probability.items():
            print(k, v)
        print()

        # dictionary that contains the number of occurences of each word per article category
        wordsinlabels = {}
        for k, v in labels.items():  # k=text1:v=arts
            for a, x in features.items():  # a=text1 x={word:count}
                if a == k:
                    for word in x.keys():
                        if v in wordsinlabels.keys():
                            if word in wordsinlabels[v]:
                                wordsinlabels[v][word] += 1
                            else:
                                wordsinlabels[v].update({word: 1})
                        else:
                            wordsinlabels[v] = {word: 1}

        # dictionary of probabilities for each word in a certain category P(Xi|C)
        word_in_category_probability = {}
        for k, v in wordsinlabels.items():
            for x, y in v.items():
                if x in word_in_category_probability.keys():
                    word_in_category_probability[x].update({k: y / len([x for x in labels if labels[x] == k])})
                else:
                    word_in_category_probability[x] = {k: y / len([x for x in labels if labels[x] == k])}

        print('\n=== P(Wort|Kategorie) ===')
        for k in list(word_in_category_probability)[:30]:
            for x, y in word_in_category_probability[k].items():
                print('P(' + str(k) + '|' + str(x) + ') = ' + str(y))
        print()

        self.model = category_probability, word_in_category_probability, wordmap

    def apply(self, features):
        """
        applies a classifier to a set of documents. Requires the classifier
        to be trained (i.e., you need to call train() before you can call apply()).

        @type features: dict
        @param features: Each entry in 'features' represents a document
                         by its (sparse) bag-of-words vector. 'features'
                         is of the following form (i.e., for each document,
                         all terms occurring in the document and their
                         counts are stored):
                         {
                           'doc1.txt':
                              {
                                'the' : 17,
                                'world': 3,
                                ...
                              },
                           'doc2.txt':
                              {
                                'community' : 2,
                                'college': 1,
                                ...
                              },
                            ...
                         }
        @rtype: dict
        @return: For each document classified, apply() returns the label
                 of the class the document has been assigned to. The return value
                 is a dictionary of the form:
                 {
                   'doc1.txt': 'arts',
                   'doc2.txt': 'travel',
                   'doc3.txt': 'sports',
                   ...
                 }
        """
        article_category_dict = {}
        category_probability = self.model[0]
        word_in_category_probability = self.model[1]

        print('\n=== Ergebnisse ===')

        for doc_name, content in features.items():

            category_with_highest_probability = None
            max_probability = 0

            for cat, prob in category_probability.items():

                print('\nCATEGORY: ' + str(cat))

                probability = prob * 100000  # P(Kategorie), keine Ahnung, wie und wo man log verwenden sollte,
                                             # es funkt einfach nicht und die Zahlen sind zu klein

                for word, cat_prob_dict in word_in_category_probability.items():  # Aufmultiplizieren
                    if cat in cat_prob_dict.keys():
                        if word in content.keys():  # wenn das Wort im Artikel enthalten ist
                            probability *= cat_prob_dict[cat] # normale Wahrscheinlichkeit P(Wort|Kategorie)
                        else:
                            probability *= 1 - cat_prob_dict[cat]  # Gegenwahrscheinlichkeit 1-P(Wort|Kategorie)

                print('PROBABILITY OF ' + str(cat) + ' : ' + str(probability))
                print('MAX_PROB: ' + str(max_probability))

                if category_with_highest_probability is None or probability >= max_probability:
                    category_with_highest_probability = cat
                    max_probability = probability

            article_category_dict[doc_name] = category_with_highest_probability
            print(str(doc_name) + ' - ' + str(category_with_highest_probability))

        return article_category_dict


class NaiveBayesClassifier(DocumentClassifier):

    def __init__(self):
        # FIXME: implement (Exercise 03)
        raise NotImplementedError()

    def train(self, features, labels, wordmap):
        # FIXME: implement (Exercise 03)
        raise NotImplementedError()

    def apply(self, features):
        # FIXME: implement (Exercise 04)
        raise NotImplementedError()


if __name__ == "__main__":

    # parse command line arguments (no need to touch)
    parser = argparse.ArgumentParser(description='A document classifier.')
    parser.add_argument('documents', metavar='doc', type=str, nargs='+',
                        help='documents to train/apply the classifier on/to')
    parser.add_argument('--train', help="train the classifier", action='store_true')
    parser.add_argument('--apply', help="apply the classifier (you'll need to train or load" \
                                        "a trained model first)", action='store_true')

    args = parser.parse_args()

    # reads and preprocesses the documents listed as commandline arguments.
    # You can use the resulting features for classification.
    if os.path.exists('cache.p'):
        with open('cache.p', 'rb') as stream:
            wordmap, features = pickle.load(stream)
    else:
        wordmap, features = read_and_preprocess_documents(args.documents)
        with open('cache.p', 'wb') as stream:
            pickle.dump((wordmap, features), stream)

    # estimate class labels ('arts', 'business', 'dining', ...)
    # from directory names
    labels = {}
    for filename in features:
        tokens = filename.split("/")
        classlabel = tokens[-2]
        labels[filename] = classlabel

    # FIXME: have a look at 'features' and 'labels'

    classifier = DocumentClassifier()

    #  train classifier on 'features' and 'labels'
    # (using documents from the 'train' folder)
    if args.train:
        classifier.train(features, labels, wordmap)
        classifier.save('Classifier')

    # apply the classifier to documents from
    # the 'test' folder
    if args.apply and os.path.exists('Classifier.p'):
        classifier.load('Classifier')
        result = classifier.apply(features)

    # FIXME: measure error rate on 'test' folder

