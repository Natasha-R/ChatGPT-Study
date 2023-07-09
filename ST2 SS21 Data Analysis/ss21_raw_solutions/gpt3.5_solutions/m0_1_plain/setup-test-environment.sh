#!/usr/bin/env bash


print_all_files () {
    for entry in "$1"/*;
    do
        if [ -d "${entry}" ] ; then
            print_all_files "$entry"
        else
            if [ -f "${entry}" ]; then
                echo "$entry"
            fi
        fi
    done
}

# Define Code Repo URLs
# Delete the protocol at the start (e.g. https://)
REPO_URL=$(echo $CODE_REPO_URL | grep -Eo '\/\/.*$' | grep -Eo '[^\/].*$')    
# Extract the repo name       
REPO_NAME=$(echo $REPO_URL | grep -Eo '\/[^\/]*$' | grep -Eo '[^\/]*')
echo "Setting up test environment with Repo URL: $REPO_URL and Repo Name: $REPO_NAME"

# Define root directory
ROOT_DIR="$(pwd)"

# Clone Repo which contains code
git clone "https://gitlab-ci-token:${CI_JOB_TOKEN}@$REPO_URL"

# Copy code files with override from cloned repo
cp -R "$ROOT_DIR/$REPO_NAME/src/main" "$ROOT_DIR/src"

# Copy test files without override from cloned repo
cp -R -n "$ROOT_DIR/$REPO_NAME/src/test" "$ROOT_DIR/src"

echo "The following files exist within the src folder:"
print_all_files "$ROOT_DIR/src"
