package calc;

public class HashTable {
    int max_size = 100;
    UserDefined hash_arr[] = new UserDefined[max_size];

    //hash function
    int generate_hash(String var) {
        long hash = 1;
        int p = 53;
        int m = (int) (1e5 + 9);
        long pow_p = 1;
        for (int i = 0; i < var.length(); i++) {
            hash = (hash + (var.charAt(i) - 'a' + 1) * pow_p) % m;
            pow_p = (pow_p * p) % m;   
        }

        return (int)(hash % max_size);
    }

    void insert(UserDefined var) {
        int hash = generate_hash(var.name);
        if (hash_arr[ hash] != null) {
            // collision
            int i = 1;
            while (hash_arr[hash] != null) {
                hash = (hash + i * i) % max_size;  //quadratic probing
                i++;
            }
        }
        hash_arr[hash] = var;
    }

    UserDefined search(String var_name) {
        int hash = generate_hash(var_name);

        if (hash_arr[hash] != null && hash_arr[hash].name.equals(var_name)) {
            return (hash_arr[hash]);
        } 
        else 
        {
            int n = 0;
            int i = 0;
            while (n < max_size) 
            {
                hash = (hash + i * i) % max_size;
                if (hash_arr[hash] != null && hash_arr[hash].name.equals(var_name)) 
                    return (hash_arr[hash]);
                n++;
            }
            return (null);
        }

    }

}


