from tree_sitter import Language, Parser

class ASTParser:
    def parse(self, code):
        parser = Parser()
        # load language later
        return parser.parse(bytes(code, "utf8"))