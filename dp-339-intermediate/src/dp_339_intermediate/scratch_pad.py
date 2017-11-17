def get_most(constructed_list, possible_pairs):
    if possible_pairs is []:
        return len(constructed_list)
    else:
        max = 0
        for pair in possible_pairs:
            new_list = constructed_list.append(pair)
            new_possibles = remove_overlaps(pair, possible_pairs)
            val = get_most(new_list, new_possibles)
            if val > max:
                max = val
        return max
