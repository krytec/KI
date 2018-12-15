#!/usr/bin/env python
# -*- coding: iso-8859-1 -*-

from preprocess_documents import read_and_preprocess_documents
import sys
import argparse

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
        # FIXME: implement

    def train(self, features, labels):
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


        #dictionary that cointains all words per article category
        wordsinlabels = {}
        for k,v in labels.items(): #k=text1:v=arts
            for a,x in features.items(): #a=text1 x={word:count}
                if(a == k):
                    for word in x.keys():
                        if(v in wordsinlabels.keys()):
                            if(word in wordsinlabels[v]):
                                wordsinlabels[v][word] += x[word]
                            else:
                                wordsinlabels[v].update({word:x[word]})
                        else:
                            wordsinlabels[v] = {word:x[word]}

        #percentage for every word in the article
        for k,v in wordsinlabels.items():
            for i,x in v.items():
                wordsinlabels[k][i]= x/len(wordsinlabels[k])
        #print(wordsinlabels)
        #wahrscheinlichkeit mit der ein artikel eine bestimmte kategorie ist
        plabels ={}

        for k,v in labels.items():
            if v in plabels.keys():
                plabels[v] += 1
            else:
                plabels[v] = 1
        for k,v in plabels.items():
            plabels[k] /= len(features)

        print(plabels)
        # saves the % how often a word is connected to an article p(w/c) = p(c)*p(c/w)
        pwlabels = {}
        for k,v in wordsinlabels.items():
            for i,x in v.items():
                if i in pwlabels.keys():
                    pwlabels[i].update({k:wordsinlabels[k][i] * plabels[k]})
                else:
                    pwlabels[i] = {k:wordsinlabels[k][i] * plabels[k]}
        print(pwlabels)
        #raise NotImplementedError()
        # FIXME: implement

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
        raise NotImplementedError()
        # FIXME: implement


class NaiveBayesClassifier(DocumentClassifier):

    def __init__(self):
        # FIXME: implement (Exercise 03)
        raise NotImplementedError()

    def train(self, features, labels):
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
    wordmap,features = read_and_preprocess_documents(args.documents)

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
        classifier.train(features, labels)

    # apply the classifier to documents from
    # the 'test' folder
    if args.apply:
        result = classifier.apply(features)

    # FIXME: measure error rate on 'test' folder

