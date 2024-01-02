from gensim.models import KeyedVectors

#3.1.1
def find_similar_word(model_path, word):
    # Load the Word2Vec model
    model = KeyedVectors.load_word2vec_format(model_path, binary=False)

    # Find the most similar word
    similar_word, _ = model.most_similar(word, topn=1)[0]

    return similar_word

# Example usage:
model_path = '/home/iasmina/AN3/projet programmation/frWac_non_lem_no_postag_no_phrase_200_skip_cut100.txt'
word_to_find = 'chat'
similar_word = find_similar_word(model_path, word_to_find)
print(f"The word similar to '{word_to_find}' is '{similar_word}'")

#3.2
def word_similarity(model_path, word1, word2):
    # Load the Word2Vec model
    model = KeyedVectors.load_word2vec_format(model_path, binary=False)

    # Calculate the similarity between two words
    similarity_score = model.similarity(word1, word2)

    return similarity_score

# Example usage:
model_path = '/home/iasmina/AN3/projet programmation/frWac_non_lem_no_postag_no_phrase_200_skip_cut100.txt'
word1 = 'chat'
word2 = 'chien'
similarity_score = word_similarity(model_path, word1, word2)
print(f"Similarity between '{word1}' and '{word2}': {similarity_score}")

#3.2
def top_similar_words(model_path, word, top_n=10):
    # Load the Word2Vec model
    model = KeyedVectors.load_word2vec_format(model_path, binary=False)

    # Get the top N similar words
    similar_words = model.most_similar(word, topn=top_n)

    return similar_words

# Example usage:
model_path = '/home/iasmina/AN3/projet programmation/frWac_non_lem_no_postag_no_phrase_200_skip_cut100.txt'
word_to_find = 'chat'
similar_words = top_similar_words(model_path, word_to_find, top_n=5)
for similar_word, score in similar_words:
    print(f"Similar Word: {similar_word}, Score: {score}")

